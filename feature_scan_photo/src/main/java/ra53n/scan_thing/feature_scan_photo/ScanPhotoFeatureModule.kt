package ra53n.scan_thing.feature_scan_photo

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ra53n.scan_thing.feature_scan_photo.data.ScanPhotoRepositoryImpl
import ra53n.scan_thing.feature_scan_photo.data.ScanPhotoService
import ra53n.scan_thing.feature_scan_photo.domain.ScanPhotoInteractor
import ra53n.scan_thing.feature_scan_photo.domain.ScanPhotoInteractorImpl
import ra53n.scan_thing.feature_scan_photo.domain.ScanPhotoRepository
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ScanPhotoFeatureProvidesModule {

    @Provides
    fun provideScanPhotoService(retrofit: Retrofit): ScanPhotoService {
        return retrofit.create(ScanPhotoService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ScanPhotoFeatureBindsModule {
    @Binds
    abstract fun bindScanPhotoRepository(impl: ScanPhotoRepositoryImpl): ScanPhotoRepository

    @Binds
    abstract fun bindScanPhotoInteractor(impl: ScanPhotoInteractorImpl): ScanPhotoInteractor
}