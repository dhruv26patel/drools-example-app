# Drools Example App
This is a simple spring boot exmaple of drool (rule engine). 

#

### Why use rule Engine?
It can be use to externzlize business logic from the application. Instead of using if-else statements you can use drools or excel file. 

Example: 
- store the file in S3 or config server and create the KieSession Bean on update. - Planning to add this soon

#

### How to use rule Engine?
You can pass an [object](https://github.com/dhruv26patel/drools-example-app/blob/master/drools-exmaple/src/main/java/com/example/droolsexmaple/model/Pet.java#L3) to the rule engine peform [action](https://github.com/dhruv26patel/drools-example-app/blob/master/drools-exmaple/src/main/resources/pets.drl#L10) based on the can [condition](https://github.com/dhruv26patel/drools-example-app/blob/master/drools-exmaple/src/main/resources/pets.drl#L8)

