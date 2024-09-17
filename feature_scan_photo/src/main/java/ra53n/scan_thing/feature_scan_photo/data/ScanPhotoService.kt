package ra53n.scan_thing.feature_scan_photo.data

import okhttp3.MultipartBody
import okhttp3.RequestBody
import ra53n.scan_thing.feature_scan_photo.BuildConfig
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ScanPhotoService {

    @Multipart
    @POST("/api/v1/objects/detect")
    suspend fun detectObjects(
        @Query("oauth_provider") oauthProvider: String = "mcs",
        @Query("oauth_token") token: String = BuildConfig.API_KEY,
        @Part image: MultipartBody.Part,
        @Part("meta") meta: RequestBody
    ): DetectObjectsResultResponse
}