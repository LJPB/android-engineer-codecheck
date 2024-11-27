package jp.co.yumemi.android.code_check.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.data.repository.http.common.client.HttpClient
import jp.co.yumemi.android.code_check.data.repository.http.ktor.client.GitHubHttpClientProvider
import jp.co.yumemi.android.code_check.data.repository.http.ktor.client.KtorClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpModule {
    @Provides
    @Singleton
    fun provideHttpRequestExecutor(): HttpClient = KtorClient(GitHubHttpClientProvider)
}
