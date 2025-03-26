package com.example.hershield

/*
 * Contact model class for emergency contacts
 */
class ContactModel(
    val id: Int,
    val name: String,
    rawPhoneNo: String  // Using a different parameter name
) {
    // Declare phoneNo as a property and initialize it directly
    val phoneNo: String = validate(rawPhoneNo)

    // Validate and format the phone number
    private fun validate(phone: String): String {
        // Creating StringBuilder for both cases
        val case1 = StringBuilder("+91")
        val case2 = StringBuilder("")

        // Check if the string already has a "+"
        if (phone.firstOrNull() != '+') {
            // For numbers without country code, add +91 and remove spaces/dashes
            phone.forEach { char ->
                if (char != '-' && char != ' ') {
                    case1.append(char)
                }
            }
            return case1.toString()
        } else {
            // For numbers with country code, just remove spaces/dashes
            phone.forEach { char ->
                if (char != '-' && char != ' ') {
                    case2.append(char)
                }
            }
            return case2.toString()
        }
    }
}