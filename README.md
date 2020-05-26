# Komoot

Using Selenium with Java and a website of your choosing (i would suggest https://www.komoot.com/, because of easily identifiable objects), please, automate a maximum of 5 test cases, which use/showcase the following techniques/features/requirements:

- test dynamic content
- apply the concept of widgets(be careful, because it comes from appium!!!)
- if you have a list of contact names, which has more entries than can fit on one page(you have to load more contacts to see them) and can be sorted by family name or by first name. Verify that - both sorting options work.
- make sure that your tests modify data during execution, but still retain their scalability.
 
The requirements for a successfully finished task are:

- Code is written in Java with TestNG and Gradle!
- You use PageObject/PageFactory Structure
- There is documentation for your automation(software, hardware and configuration prerequisites for running the tests; how to run the tests; etc.) in the form of a README.MD file
- add step by step description of your test cases in separate text files.
- You publish your code on GitHub
- your push your code to GitHub at latest starting with the first out of three test cases, which you implemented.(so that we can see the commit history)
- Your code can be executed according to the the documentation in the README.MD
- Tests are all green, unless there is a bug in the app.

############################################################################################################################################################
# Instructions

1) Requirements:
- Windows machine;
- Google Chrome installed (if want to run with Chrome);
- Firefox installed (if you want to run with Firefox).

2) RunTests:
- Validate that your %JAVA_HOME% is configured with the jdk path (java 8 or later) and it's added in the variable %PATH%;
- Run batch file "run_tests.bat" in project root folder (it should build, download dependencies-if needed and run tests);
- You can run the tests with an eclipse or similar IDE with TestNG add-in installed (click in the src\test\resources\com\komoot\testng\xml\komoot_tests.xml)
with right mouse button,  Run As, TestNG Suite;
- You can open one of test files and run directly too using the IDE (src\test\java\com\komoot\tests) with right mouse button,  Run As, TestNG Test.

3) Config
- You can change browser config and url here: config\config.properties
Chrome valid inputs: gc, googlechrome,chrome
Firefox valid inputs: ff,firefox,mozillafirefox

4) Test Scenarios:
- Please check Komoot.feature (src\test\resources\features\).

5) Knew Issues
- If you have some issue with the webdrivers you may go to webdriver folder in .m2\repository\webdriver and delete it to force new download.

