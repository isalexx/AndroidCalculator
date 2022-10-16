package ca.georgiancollege.androidcalculator

class Utility {
    fun findMatchInArray(haystack: List<String>, needle: String): Boolean{
        if (haystack.contains(needle)){
            return true;
        }

        return false;
    }
}