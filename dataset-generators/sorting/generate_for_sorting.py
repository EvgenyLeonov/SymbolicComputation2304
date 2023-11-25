import random

COUNT = 10000

vals = []
cnt = 0
for n in range(COUNT):
    cnt += 1
    vals.append(random.randint(0, 1000))
    #if cnt // 1000 == 0:
    #    print(cnt)

s = "(def large_vector ["
cnt = 0
for n in vals:
    cnt += 1
    s = s + " " + str(n)
    #if cnt // 1000 == 0:
    #    print(cnt)

s = s + "])"

print(s)
