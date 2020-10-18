package furhatos.app.fruitseller

import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class AffwildModel {

    companion object {

        /**
         * Returns the outputs of the Affwild model as a list of pairs where the first value in the pair is the valence and the second is the arousal.
         * The list is sorted from old to new.
         */
        fun getOutputs(): List<Pair<Float, Float>> {
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
    }
}