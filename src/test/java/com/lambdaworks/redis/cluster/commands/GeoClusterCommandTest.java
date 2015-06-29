package com.lambdaworks.redis.cluster.commands;

import static com.lambdaworks.redis.cluster.ClusterTestUtil.flushClusterDb;

import java.util.concurrent.TimeUnit;

import com.lambdaworks.redis.cluster.api.StatefulRedisClusterConnection;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.lambdaworks.redis.GeoCommandTest;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.TestSettings;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.cluster.ClusterTestUtil;
import com.lambdaworks.redis.cluster.RedisClusterClient;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 */
public class GeoClusterCommandTest extends GeoCommandTest {
    private static RedisClusterClient redisClusterClient;
    private StatefulRedisClusterConnection<String, String> clusterConnection;

    @BeforeClass
    public static void setupClient() {
        redisClusterClient = new RedisClusterClient(RedisURI.Builder.redis(TestSettings.host(), TestSettings.port(900)).build());
    }

    @AfterClass
    public static void closeClient() {
        redisClusterClient.shutdown(0, 0, TimeUnit.SECONDS);
    }

    @Before
    public void openConnection() throws Exception {
        redis = connect();
        flushClusterDb(clusterConnection);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RedisCommands<String, String> connect() {
        clusterConnection = redisClusterClient.connectCluster().getStatefulConnection();
        return ClusterTestUtil.redisCommandsOverCluster(clusterConnection);
    }

}