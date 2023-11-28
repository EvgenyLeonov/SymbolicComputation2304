import random

COUNT = 10000

vals = []
vals_almost_sorted = []
cnt = 0
for n in range(COUNT):
    cnt += 1
    vals.append(random.randint(0, 1000))
    vals_almost_sorted.append(cnt)
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

############
s = "(def large_vector_almost_sorted ["
cnt = 0
for n in vals_almost_sorted:
    cnt += 1
    s = s + " " + str(n)
    #if cnt // 1000 == 0:
    #    print(cnt)

s = s + "])"

print(s)

############

s = "const largeList = ["
cnt = 0
for n in vals:
    cnt += 1
    if cnt > 1:
        s = s + ", "
    s = s + str(n)
    #if cnt // 1000 == 0:
    #    print(cnt)

s = s + "];"

print(s)

############

s = "const largeListAlmostSorted = ["
cnt = 0
for n in vals_almost_sorted:
    cnt += 1
    if cnt > 1:
        s = s + ", "
    s = s + str(n)
    #if cnt // 1000 == 0:
    #    print(cnt)

s = s + "];"

print(s)
