
a = [2, 5, 4, 25, 16, 9, 58858, 81, 255, 12, 121]

def gift_distribution(e, a, temp):
    if e in a:
        a.remove(e)
        temp.append(e)
        return loop(e*e, a, temp)
    else:
        return a, temp

def gift_package(a, temp, la):
    if len(a) == 0:
        return la
    a.sort()
    while a:
        i = a[0]
        gift_distribution(i, a, temp)
        if len(temp):
            t = copy.deepcopy(temp)
            la.append(t)
            temp.clear()
        return pricre(a, temp, la)

la = []
temp =[]
print(gift_package(a, temp, la))
