from __builtin__ import list
from bdb import foo

__author__ = 'prakhash'

import urllib2
import MySQLdb
import requests
from bs4 import BeautifulSoup

actualFoodList=[]


def checkInAddressOne(url, foodnamelist):
    urlCount=0;
    while(urlCount<len(url)):
        if(url=="http://www.google.com"):
            response = urllib2.urlopen(url)
            html = response.read()
            html1 = str(html)
            count=0
            while(count<len(foodnamelist)):
                res = html1.find(foodnamelist[count])
                if (res >= 0):
                    actualFoodList.append(foodnamelist[count])
                    foodnamelist.remove(foodnamelist[count])
                    count+=1
                else:
                    count+=1
        else:
            getFromGoogle(foodnamelist)


def getFromGoogle(foodnamelist):

    foodlistcount=0;
    while(foodlistcount<len(foodnamelist)):
        listOfWords=makeCombinations(foodnamelist[foodlistcount])
        count=0;
        while(count<len(listOfWords)):
            r = requests.get('http://www.google.com/search',
                         params={'q':'"'+listOfWords[count]+'"',
                                 "tbs":"li:1",
                                 "API key":"AIzaSyAYUEFp10MS1UQPClnaPvinJlPJnkzUE6Q"
                                 }
                        )
            soup = BeautifulSoup(r.text,"lxml")
            numOfOccurence=soup.find('div',{'id':'resultStats'}).text
            count+=1
        if(numOfOccurence>5):
            actualFoodList.append(foodnamelist[foodlistcount])
            foodnamelist.remove(foodnamelist[foodlistcount])
            foodlistcount+=1
        else:
            foodlistcount+=1


def makeCombinations(findword):
    wordsInFindword = findword.split(" ")
    includeForSearching = []

    if (len(wordsInFindword) > 2):
        count = 0;
        while (count < len(wordsInFindword)):
            includeForSearching.append(wordsInFindword[count] + " is a food")
            includeForSearching.append(wordsInFindword[count] + " is a Recipe")
            count += 1
    else :
        if(len(wordsInFindword) == 2):
            includeForSearching.append(wordsInFindword[0] + " is a food")
            includeForSearching.append(wordsInFindword[0] + " is a Recipe")
        else:
            includeForSearching.append(wordsInFindword[0] + " is a food")
            includeForSearching.append(wordsInFindword[0] + " is a Recipe")

    return includeForSearching



def readData():
    db = MySQLdb.connect("localhost", "root", "", "Foodcollection")
    cursor = db.cursor()

    sql = "SELECT * FROM myviewname5"
    list = []
    f=open("foodnamelarge.txt",'w')
    try:
        cursor.execute(sql)
        results = cursor.fetchall()
        for row in results:
            food_name = row[0]
            f.write(food_name+"\n")
            list.append(food_name)
    except:
        print("error")

    db.close()
    f.close()
    return list

def writeData():
    db = MySQLdb.connect("localhost", "root", "", "Foodcollection")
    cursor = db.cursor()

    list = []
    f=open("Data",'r+')


    for line in f:
        list.append(line.split("\n")[0])

    try:
        for line in list:
            print(line)
            cursor.execute('INSERT INTO final_food_names(names) VALUES("%s")' %(line))
            db.commit()
    except:
        # Rollback in case there is any error
         db.rollback()

    db.close()
    f.close()





def CompareList(list, links):
    for x in range(0, len(list)):
        count = 0;
        available = False
        while (count < len(links)):
            available = checkInAddressOne(links[count], list[x])
            count += 1


links = ["http://www.foodtimeline.org/foodfaqindex.html"]


print("Execution started")
list = readData()
writeData()
print("Finished")

