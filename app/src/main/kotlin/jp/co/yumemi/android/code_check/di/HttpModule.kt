package jp.co.yumemi.android.code_check.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.data.repository.http.common.client.HttpRequestClient
import jp.co.yumemi.android.code_check.data.repository.http.ktor.client.KtorRequestClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpModule {
    @Provides
    @Singleton
    fun provideHttpRequestClient(): HttpRequestClient = KtorRequestClient()
}
