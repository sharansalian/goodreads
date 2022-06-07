package io.sharan.goodreads.framework

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // app-level container which other containers can access
class GoodReadsApplication: Application()
