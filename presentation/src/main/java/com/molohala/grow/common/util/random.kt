package com.molohala.grow.common.util

import java.util.Random

fun IntRange.random() =
       Random().nextInt((endInclusive + 1) - start) + start
