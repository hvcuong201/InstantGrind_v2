package fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.demarkelabs.android_parsesdk_kotlin.*
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.livequery.ParseLiveQueryClient
import com.parse.livequery.SubscriptionHandling
import org.w3c.dom.Comment

open class FeedFragment : Fragment() {
    lateinit var swipeContainer: SwipeRefreshLayout
    lateinit var postsRecyclerView: RecyclerView
    lateinit var adapter: PostAdapter
    var allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            queryPosts()
            swipeContainer.setRefreshing(false)
        }
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

        postsRecyclerView = view.findViewById(R.id.postRecyclerView)
        adapter = PostAdapter(requireContext(), allPosts)
        postsRecyclerView.adapter = adapter
        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        queryPosts()
    }

    // Query for all posts in the server
    open fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // Find all Post objects
        query.include(Post.KEY_USER)
        query.limit = 20
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e != null) {
                    Log.e(TAG, "Error fetching posts")
                } else {
                    if (posts != null) {
                        for (post in posts) {
                            Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser()?.username)
                        }
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    companion object {
        const val TAG = "FeedFragment"
    }
}