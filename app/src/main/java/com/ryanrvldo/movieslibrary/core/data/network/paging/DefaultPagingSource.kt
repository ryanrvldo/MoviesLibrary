package com.ryanrvldo.movieslibrary.core.data.network.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ryanrvldo.movieslibrary.core.data.network.paging.DefaultPagingSource.Companion.PAGING_CONFIG
import com.ryanrvldo.movieslibrary.core.data.network.response.PagingResponse

internal class DefaultPagingSource<R : Any>(
    private val apiCall: suspend (page: Int) -> PagingResponse<R>
) : PagingSource<Int, R>() {
    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            initialLoadSize = 40,
            maxSize = 50,
            prefetchDistance = 5
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, R> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = apiCall.invoke(nextPageNumber)
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (nextPageNumber == response.totalPages) null else nextPageNumber + 1
            )
        } catch (exception: Exception) {
            // IOException for network failures.
            // HttpException for any non-2xx HTTP status codes.
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, R>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

internal fun <V : Any> buildPager(
    apiCall: suspend (page: Int) -> PagingResponse<V>
): Pager<Int, V> = Pager(PAGING_CONFIG) { DefaultPagingSource(apiCall) }
