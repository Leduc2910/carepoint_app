package com.hau.carepointtmdt.network

import android.content.Context
import com.cloudinary.android.MediaManager

object CloudinaryConfig {
    fun initializeCloudinary(context: Context) {
        val config: MutableMap<String, String> = HashMap()
        config["cloud_name"] = "carepointstorage"
        config["api_key"] = "284291112176175"
        config["api_secret"] = "NUpmLxYOTcO26FKDEz1ECbrmk_8"
        MediaManager.init(context, config)
    }
}