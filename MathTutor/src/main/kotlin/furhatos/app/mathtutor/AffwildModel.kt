package furhatos.app.fruitseller

import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class AffwildModel {

    companion object {

        private var previousFrustration = 0.0

        /**
         * Returns the outputs of the Affwild model as a list of pairs where the first value in the pair is the valence and the second is the arousal.
         * The list is sorted from old to new.
         */
        private fun getOutputs(): List<Pair<Float, Float>> {
            val url = URL("http://localhost:5000/predictions")
            try {
                with(url.openConnection() as HttpURLConnection) {
                    requestMethod = "GET"  // optional default is GET

                    var jsonStr = ""
                    inputStream.bufferedReader().use {
                        it.lines().forEach { line ->
                            jsonStr += line
                        }
                    }

                    val jsonObject = JSONObject(jsonStr)
                    val outputs = emptyList<Pair<Float, Float>>().toMutableList()
                    for (output in jsonObject.getJSONArray("outputs")) {
                        val outputValuesList = output as JSONArray

                        outputs += Pair((outputValuesList[0] as String).toFloat(), (outputValuesList[1] as String).toFloat())
                    }

                    return outputs
                }
            } catch (e: Exception) {
                System.err.println("Warning! Could not get results from the Affwild model! Is the server running?")
                return emptyList()
            }
        }

        fun valenceArousalToFrustration(valence: Float, arousal: Float): Double {
            return -1.5*valence*(arousal*0.5+0.5)+1.5
        }

        /**
         * Returns the maximum frustration that has been meassured by the model since the last update.
         * Frustration level can be 0, 1 or 2 where 0 is happy, 1 is neutral and 2 is frustrated.
         */
        fun updateAndGetFrustration(): Double {

            val values = getOutputs()

            if (values.isEmpty()) {
                return previousFrustration
            }

            var maximumFrustration = 0.0
            for (value in values) {
                val frustration = valenceArousalToFrustration(value.first, value.second)
                if (frustration > maximumFrustration) {
                    maximumFrustration = frustration
                }
            }

            return maximumFrustration

        }
    }
}