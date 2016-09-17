package rebootserver;

import java.util.LinkedHashMap;

import cn.nukkit.Server;
import cn.nukkit.plugin.*;
import cn.nukkit.scheduler.*;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase {
	private Config config;
	
	@SuppressWarnings("serial")
	public void onEnable() {
		getDataFolder().mkdirs();
		config = new Config(getDataFolder() + "/config.yml", Config.YAML, new ConfigSection(new LinkedHashMap<String, Object>() {
			{
				put("restart-minute", 30);
			}
		}));
		
		getServer().getScheduler().scheduleDelayedTask(new ServerStopLoadTask(this), 20 * 60 * config.getInt("restart-minute"));
	}
}

class ServerStopLoadTask extends PluginTask<Main> {
	protected Main owner;
	public ServerStopLoadTask(Main owner) {
		super(owner);
	}

	@Override
	public void onRun(int currentTick) {
		Server.getInstance().broadcastMessage(TextFormat.RED + "잠시후 서버가 재부팅 됩니다. 서버가 종료되면 다시 접속해주세요.");
		Server.getInstance().getScheduler().scheduleDelayedTask(new ServerStopTask(getOwner()), 20 * 5);
	}
	
}

class ServerStopTask extends PluginTask<Main> {
	public ServerStopTask(Main owner) {
		super(owner);
	}

	@Override
	public void onRun(int currentTick) {
			Server.getInstance().broadcastMessage(TextFormat.RED + "서버가 재부팅 됩니다.");
		Server.getInstance().shutdown();
	}
	
}