package com.us.starchasers.gm_utils.data.remote.responses

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)