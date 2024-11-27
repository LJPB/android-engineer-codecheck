package jp.co.yumemi.android.code_check.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.data.repository.http.common.executor.HttpRequestExecutor
import jp.co.yumemi.android.code_check.data.repository.http.github.request.repository_search.GitHubRepositorySearchApi
import jp.co.yumemi.android.code_check.data.repository.http.github.request.repository_search.GitHubRepositorySearchService

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Provides
    fun provideGitHubRepositorySearchService(httpRequestExecutor: HttpRequestExecutor): GitHubRepositorySearchService =
        GitHubRepositorySearchApi(httpRequestExecutor)
}
