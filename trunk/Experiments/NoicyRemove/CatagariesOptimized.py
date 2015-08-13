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

def ReadFile():
    listOfNames = []
    file = open('FinalNames.txt', 'r')

    for line in file:
        listOfNames.append(line),

    return listOfNames

def DefineList(name1, contentbody):
    name=name1.upper()
    f1=open("Foods.txt",'a')
    f2=open("Beverage.txt",'a')
    f3=open("Appetizer.txt",'a')
    f4=open("Other.txt",'a')
    name=name.upper()
    if (("JUIC" in name)|("SODA" in name)|("BRANDY" in name)|("BOUIL" in name)|("DRINK" in name)|("VOD" in name)|("WINE" in name)|("BEER" in name)|("WATER" in name)|("LEMON" in name)|("BEVERAGE" in name)|("ICE CREAM" in name)|("JUICY" in name)|("ALCHOHOL" in name)|("TEA" in name)|("COFFE" in name)|("MILK" in name)):
        BeverageList.append(name)
        f2.write(name1)
        return True
    elif (("GRAM" in name)|("BEET" in name)|("PIZZ" in name)|("CURD" in name)|("BURG" in name)|("SEA" in name)|("SESAM" in name)|("BONIAT" in name)|("BONE" in name)|("BRACCOLI" in name)|("BARLEY" in name)|("FOOD" in name)|("NOODL" in name)|("CURD" in name)|("YOG" in name) | ("COOKIE" in name) | ("COOKIES" in name) | ("DISH" in name) |("PUDDING" in name)|("SOUP" in name)
    |("BREAD" in name) |("BBQ" in name) | ("CHEESE" in name) |("BISCUIT" in name) |("BEEF" in name) | ("CHICKEN" in name) |("PORK" in name)|("BEAN" in name)|("CAK" in name)
    |("CHEESE" in name) |("CHEES" in name)|("BUN" in name)|("POTATO" in name)|("TOMATO" in name)|("SANDWICH" in name)|("MUSHROOM" in name)|("BRYONI" in name)|("PUDDING" in name)|("PIE" in name)|("FISH" in name)
            |("RICE" in name)|("BUTTER" in name)|("TURKEY" in name)|("FLOUR" in name)|("PASTR" in name)|("SHRIMP" in name)|("ROLL" in name)|("SPRING" in name)|("SALAD" in name)|("TANDO0" in name)|("TOAST" in name)|("FRIED" in name)|("TORT" in name)|("MUTTON" in name)|("MEAL" in name)|("MEAT" in name)|("PUD" in name)|("EGG" in name)|("TUNA" in name)|("CRAB" in name)|("MUFFIN" in name)|("RICE" in name)):
        FoodList.append(name)
        f1.write(name1)
        return True
    elif (("SESAM" in contentbody)|("BONIAT" in contentbody)|("BONE" in contentbody)|("BRACCOLI" in contentbody)|("BARLEY" in contentbody)|("FOOD" in contentbody)|("NOODL" in contentbody)|("CURD" in contentbody)|("YOG" in contentbody) | ("COOKIE" in contentbody) | ("COOKIES" in contentbody) | ("DISH" in contentbody) |("PUDDING" in contentbody)|("SOUP" in contentbody)
    |("BREAD" in contentbody) |("BBQ" in contentbody) | ("CHEESE" in contentbody) |("BISCUIT" in contentbody) |("BEEF" in contentbody) | ("CHICKEN" in contentbody) |("PORK" in contentbody)|("BEAN" in contentbody)|("CAK" in contentbody)
    |("CHEESE" in contentbody) |("CHEES" in contentbody)|("BUN" in contentbody)|("POTATO" in contentbody)|("TOMATO" in contentbody)|("SANDWICH" in contentbody)|("MUSHROOM" in contentbody)|("BRYONI" in contentbody)|("PUDDING" in contentbody)|("PIE" in contentbody)|("FISH" in contentbody)
            |("RICE" in contentbody)|("BUTTER" in contentbody)|("TURKEY" in contentbody)|("FLOUR" in contentbody)|("PASTR" in contentbody)|("SHRIMP" in contentbody)|("ROLL" in contentbody)|("SPRING" in contentbody)|("SALAD" in contentbody)|("TANDO0" in contentbody)|("TOAST" in contentbody)|("FRIED" in contentbody)|("TORT" in contentbody)|("MUTTON" in contentbody)|("MEAL" in contentbody)|
              ("MEAT" in contentbody)|("PUD" in contentbody)|("EGG" in contentbody)|("TUNA" in contentbody)|("CRAB" in contentbody)|("MUFFIN" in contentbody)|("RICE" in contentbody)):
        FoodList.append(name)
        f1.write(name1)
        return True
    elif (("BRANDY" in contentbody)|("BOUIL" in contentbody)|("DRINK" in contentbody) |("ICE CREAM" in name)| ("DRINKS" in contentbody) | ("JUICY" in contentbody) |("BEVERAGE" in contentbody)|("MILK" in contentbody)
    |("BEER" in contentbody)|("ALCHOHOL" in contentbody)|("TEA" in contentbody)):
        BeverageList.append(name)
        f2.write(name1)
        return True
    elif (("APPETIZER" in name)|("APPETIZER" in contentbody)|("SAUCE" in name)|("SAUCE" in contentbody)|("SAUC" in name)):
        AppetizerList.append(name)
        f3.write(name1)
        return True
    else:
        OtherList.append(name)
        f4.write(name1)
        return False


    f1.close()
    f2.close()
    f3.close()
    f4.close()

def sendRequest(FoodNameSend):


        contentBody = ""
        content = ""
        print("sending request.......")

        accessed1 = False
        accessed2 = False
        accessed3 = False
        accessed4 = False

        try:
            data = sendRequestWiki(FoodNameSend)
            for key, value in data["query"]["pages"].items():
                content = data["query"]["pages"][key]["revisions"]["*"]

        except:
            data1 = sendRequestWiki(FoodNameSend.split(" ")[0])
            try:
                for key, value in data1["query"]["pages"].items():
                    content = data1["query"]["pages"][key]["revisions"][0]["*"]
                    accessed1 = True;
            except:
                try:
                    if(len(FoodNameSend.split(" "))>1):
                        data2 = sendRequestWiki(FoodNameSend.split(" ")[1])
                        for key, value in data1["query"]["pages"].items():
                            content = data1["query"]["pages"][key]["revisions"][0]["*"]
                            accessed2 = True
                except:
                    contentBody=""

        if (len(content.split("\n\n")) > 2):
            contentBody = content.split("\n\n")[1]
        else:
            if (len(FoodNameSend.split(" ")) > 2 ):
                contentBody = sendNewMidRequest(FoodNameSend.split(" ")[2])
                accessed3 = True

            elif (len(FoodNameSend.split(" ")) > 1):
                contentBody = sendNewMidRequest(FoodNameSend.split(" ")[1])
                accessed4 = True

                # print(contentBody+"\n")

        if (len(contentBody.split(".")) > 1):
            contentBody = contentBody.split(".")[0]

        contentBody = re.sub(r'[^a-zA-Z0-9" "=]', '', contentBody)

        contentBody = contentBody.upper();
        print(contentBody)

        DefineList(FoodNameSend, contentBody)


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


listOfNames = ReadFile()
#
count=0;
while(count<len(listOfNames)):
    if(DefineList(listOfNames[count],"")):
        print(listOfNames[count].upper()+"  added")
    else:
        print(listOfNames[count])
        #sendRequest(listOfNames[count])
    count+=1
#
# count=0;
# while(count<len(listOfNames)):
#     sendRequest(listOfNames[count])
#     count+=1



