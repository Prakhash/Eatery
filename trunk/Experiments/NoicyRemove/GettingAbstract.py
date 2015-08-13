__author__ = 'prakhash'

from sets import Set

listavailable=set()

def ReadFile():
    listOfNames = []
    file = open('backup.txt', 'r')

    for line in file:
        listOfNames.append(line),


    return listOfNames

def ReadFile2():
    listOfNamesFoodFinal = set()
    file = open('FinalNames.txt', 'r')
    file2 = open('FinalNames1.txt', 'r+')

    for line in file:
        listOfNamesFoodFinal.add(line),

    while(len(listOfNamesFoodFinal)!=0):
        newName=listOfNamesFoodFinal.pop()
        print(newName)
        file2.write(newName)



def Categorize(listOfNames):
    count=0;
    while(count<len(listOfNames)):
        indices=listOfNames[count].split(" ")
        count1=0;
        while(count1<len(indices)):
            listavailable.add(indices[count1])
            count1+=1
        count+=1


def countCategory(listavailable):
    countAbstract=[]

    count3=0
    while(len(listavailable)!=0):
        name=listavailable.pop()
        print(name)
        countAbstract[count3].append(listavailable.pop())
        count3+=1

    print(len(countAbstract))

list=ReadFile()
Categorize(list)
print(len(listavailable))

countCategory(listavailable)




