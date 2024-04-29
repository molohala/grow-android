package com.molohala.infinity.common.util

import java.util.Random

fun IntRange.random() =
       Random().nextInt((endInclusive + 1) - start) + start
