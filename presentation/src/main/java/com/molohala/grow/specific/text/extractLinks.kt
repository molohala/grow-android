package com.molohala.grow.specific.text

import java.util.regex.Pattern

fun String.extractLinks(): List<String> {
    val urlPattern = Pattern.compile(
        "(https?://[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?)",
        Pattern.CASE_INSENSITIVE
    )
    val matcher = urlPattern.matcher(this)
    val links = mutableListOf<String>()

    while (matcher.find()) {
        links.add(matcher.group())
    }

    return links
}
