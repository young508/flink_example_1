package org.learn.Util;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.contrib.streaming.state.EmbeddedRocksDBStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.concurrent.TimeUnit;

public class CheckPointUtil {

	public static void envConfigSetting(StreamExecutionEnvironment env) {
		env.enableCheckpointing(60_000);

		CheckpointConfig checkpointConfig = env.getCheckpointConfig();
		checkpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
		checkpointConfig.setMaxConcurrentCheckpoints(1);
		checkpointConfig.setTolerableCheckpointFailureNumber(3);
		checkpointConfig.enableUnalignedCheckpoints();
		checkpointConfig.setCheckpointTimeout(1_000);
		checkpointConfig.setMinPauseBetweenCheckpoints(500);
		checkpointConfig.setExternalizedCheckpointCleanup(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
		checkpointConfig.setCheckpointStorage("file:/Users/wangyang/Documents/bigdataproject/flink_example_1" +
				"/checkpoints");


		env.setStateBackend(new EmbeddedRocksDBStateBackend());
		env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5, Time.of(30, TimeUnit.SECONDS)));
		env.setParallelism(1);
	}
}
