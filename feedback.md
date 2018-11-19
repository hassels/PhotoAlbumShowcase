I will be focusing mostly on the things that I think you can improve on. Hopefully you don't feel discouraged by seeing mostly negative feedback. However, I feel there isn't as much to gain by going over what you are doing well. Since you just need to keep doing those things. All in all your project works and you followed fairly good flow to tackle this problem.

# User Issues
* Titles have spaces removed
* Photos lose order
* I can see exceptions
* I can't see all of the photos

# Developer Issues
* No tests
* Monofile / function
* Unused vars
* `.toString()` on strings
* BufferedReader is very brittle/complex way to read things
    * Consider using a JSON parsing lib like GSON or Jackson
* Split your code up into smaller functions
    * Input reading
    * Request
    * Response parsing
    * Output / Output Formatting
* Avoid variable names that could sound like functions (action names)
    * `readLine` sounds like a function name
        * `currentLine` or even `line` might be a better name
* Avoid magic numbers
    * Why does `readLine.length() > 10` and `readLine.length() > 11` work?
    * 10 and 11 could be made constants so we know *why* they are 10 and 11
* Avoid creating variables for just renaming purposes
    * just call the inital variable that name
        ```java
        for (String name: resultMap.keySet()){
                String key = name;
                String value = resultMap.get(name);
                System.out.println(key + " " + value);
            }
        ```
    * Should probably be
        ```java
        for (String key: resultMap.keySet()){
                String value = resultMap.get(key);
                System.out.println(key + " " + value);
            }
        ```
    * Or even better
        ```java
        for ( Map.Entry<String, Tab> photo : hash.entrySet()) {
            String id = photo.getKey();
            String title = photo.getValue();
            System.out.println(id + " " + title);
        }
        ```
* Avoid `if-else` when returning
    ```java
    if(true) {
        return doSomething();
    } else {
        return doSomethingElse();
    }
    ```
    * Should probably be
    ```java
    if(true) {
        return doSomething();
    }
    return doSomethingElse();
    ```
* Avoid nested `if`s
    ```java
    if (readLine.length() > 10) {
        if (readLine.substring(0, 9).equals("    \"id\":")) {
            newId = ""; // this line doesn't really do anything*
            String formattedString = readLine.replaceAll("[, ]", "");
            String[] splitLine = (formattedString.split(":"));
            newId = splitLine[1]; // * thanks to this line
        }
    }
    ```
    * Should probably be
    ```java
    if (readLine.length() > 10 && readLine.substring(0, 9).equals("    \"id\":")) {
        String formattedString = readLine.replaceAll("[, ]", "");
        String[] splitLine = (formattedString.split(":"));
        newId = splitLine[1];
    }
    ```
    * Or even better
    ```java
    if (isIdLine(line)) {
        newId = parseId(line);
    } else if(isTitleLine(line)) {
        newTitle = parseTitle(line);
    }
    ```
* DRY it out
    * Don't
    * Repeat
    * Yourself
