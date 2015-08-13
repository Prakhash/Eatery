from curses.ascii import NUL

__author__ = 'prakhash'

import urllib2
import MySQLdb
import requests
from bs4 import BeautifulSoup
import re

FoodList = []
BeverageList = []
AppetizerList=[]
OtherList = []


def readData():
    db = MySQLdb.connect("localhost", "root", "", "Foodcollection")
    cursor = db.cursor()

    sql = "SELECT * FROM final_food_names"
    listOfNames = []

    try:
        cursor.execute(sql)
        results = cursor.fetchall()
        for row in results:
            food_name = row[0]
            listOfNames.append(food_name)
    except:
        print("error")

    db.close()
    return listOfNames


def sendRequest(listOfNames):
    count = 2644;
    # f1=open("Foods.txt",'r+')
    # f2=open("Beverage.txt",'r+')
    # f3=open("Appetizer.txt",'r+')
    # f4=open("Other.txt",'r+')
    while (count < len(listOfNames)):
        f1=open("Foods.txt",'a')
        f2=open("Beverage.txt",'a')
        f3=open("Appetizer.txt",'a')
        f4=open("Other.txt",'a')
        contentBody = ""
        content = ""
        print("sending request.......")
        print(listOfNames[count])
        accessed1 = False
        accessed2 = False
        accessed3 = False
        accessed4 = False

        try:
            data = sendRequestWiki(listOfNames[count])
            for key, value in data["query"]["pages"].items():
                content = data["query"]["pages"][key]["revisions"]["*"]

        except:
            data1 = sendRequestWiki(listOfNames[count].split(" ")[0])
            try:
                for key, value in data1["query"]["pages"].items():
                    content = data1["query"]["pages"][key]["revisions"][0]["*"]
                    accessed1 = True;
            except:
                try:
                    if(len(listOfNames[count].split(" "))>1):
                        data2 = sendRequestWiki(listOfNames[count].split(" ")[1])
                        for key, value in data1["query"]["pages"].items():
                            content = data1["query"]["pages"][key]["revisions"][0]["*"]
                            accessed2 = True
                except:
                    contentBody=""

        if (len(content.split("\n\n")) > 2):
            contentBody = content.split("\n\n")[1]
        else:
            if (len(listOfNames[count].split(" ")) > 2 ):
                contentBody = sendNewMidRequest(listOfNames[count].split(" ")[2])
                accessed3 = True

            elif (len(listOfNames[count].split(" ")) > 1):
                contentBody = sendNewMidRequest(listOfNames[count].split(" ")[1])
                accessed4 = True

                # print(contentBody+"\n")

        if (len(contentBody.split(".")) > 1):
            contentBody = contentBody.split(".")[0]

        contentBody = re.sub(r'[^a-zA-Z0-9" "=]', '', contentBody)

        contentBody = contentBody.upper();
        print(contentBody)

        DefineList(listOfNames[count], contentBody,f1,f2,f3,f4)

        count += 1
        f1.close()
        f2.close()
        f3.close()
        f4.close()
    return contentBody


def sendRequestWiki(foodName):
    try:
        r = requests.get('http://en.wikipedia.org/w/api.php',
                         params={"action": "query",
                                 "prop": "revisions",
                                 "rvprop": "content",
                                 "rvsection": 0,
                                 "titles": foodName,
                                 "format": "json"
                         }
        )
        return r.json()
    except:
        print "404"


def sendNewMidRequest(foodname):
    print(foodname)
    content = ""
    try:
        r4 = requests.get('http://en.wikipedia.org/w/api.php',
                          params={"action": "query",
                                  "prop": "revisions",
                                  "rvprop": "content",
                                  "rvsection": 0,
                                  "titles": foodname,
                                  "format": "json"
                          }
        )

        data4 = r4.json()
        for key, value in data4["query"]["pages"].items():
            content = data4["query"]["pages"][key]["revisions"][0]["*"]
    except:
        content = "404"

    return content


def DefineList(name, contentbody,f1,f2,f3,f4):
    name=name.upper()
    if (("FOOD" in name) | ("COOKIE" in name) | ("COOKIES" in name) | ("DISH" in name) |("PUDDING" in name)
    |("BREAD" in name) |("BBQ" in name) | ("CHEESE" in name) |("BISCUIT" in name) |("BEEF" in name) | ("CHICKEN" in name)):
        FoodList.append(name)
        f1.write(name+"\n")
    elif (("DRINK" in name)|("BEER" in name)):
        BeverageList.append(name)
        f2.write(name+"\n")
    elif (("APPETIZER" in name)):
        AppetizerList.append(name)
        f3.write(name+"\n")
    elif (("FOOD" in contentbody) | ("COOKIE" in contentbody) | ("DISH" in contentbody) |
        ("FOODS" in contentbody) | ("COOKIES" in contentbody) | ("DISHES" in contentbody) |
              ("CHEESE" in contentbody) | ("BUSCUIT" in contentbody)):
        FoodList.append(name)
        f1.write(name+"\n")
    elif (("DRINK" in contentbody) | ("DRINKS" in contentbody) | ("JUICY" in contentbody) |("BEVERAGE" in contentbody)
    |("BEER" in contentbody)):
        BeverageList.append(name)
        f2.write(name+"\n")
    elif (("APPETIZER" in contentbody)|("SAUCE" in name)|("SAUCE" in contentbody)):
        AppetizerList.append(name)
        f3.write(name+"\n")
    else:
        OtherList.append(name)
        f4.write(name+"\n")


def WriteData(topic,list):
    f=open("CategarizedFoods.txt",'r+')
    try:
        i=0
        f.write(topic+"\n")
        while(i<len(list)):
            f.write(list[i]+"\n")
            print(list[i])
            i+=1;
        f.write("\n\n")
    except:
        print("error")
    f.close()
    return list

listOfNames = readData()
sendRequest(listOfNames)
WriteData("Food Names",FoodList)
WriteData("Beverage Names",BeverageList)
WriteData("Appetizer Names",AppetizerList)
WriteData("Other Names",OtherList)
