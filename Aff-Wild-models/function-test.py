import matplotlib.pyplot as plt
import numpy as np

y = 0.5

def fun(b, a):
    return -1.5*a*(b*y+y)+1.5

a = np.zeros((200, 200))
for i in range(-100, 100):
    for j in range(-100, 100):
        a[i - 100, j - 100] = fun(-i/100, j/100)

print(np.min(a), np.max(a))

plt.imshow(a, cmap='hot', interpolation='nearest')
plt.show()