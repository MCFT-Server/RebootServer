package rebootserver;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.*;
import cn.nukkit.scheduler.*;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase{
	public void onEnable() {
		getServer().getScheduler().scheduleDelayedTask(new ServerStopLoadTask(this), 20*60*30);
	}
}
class ServerStopLoadTask extends PluginTask<Main> {
	protected Main owner;
	public ServerStopLoadTask(Main owner) {
		super(owner);
	}

	@Override
	public void onRun(int currentTick) {
		for (Player player : Server.getInstance().getOnlinePlayers().values()) {
			for (int i = 0; i < 5; i++) {
				player.sendMessage(TextFormat.RED + "잠시후 서버가 재부팅 됩니다. 서버가 종료되면 다시 접속해주세요.");
			}
		}
		Server.getInstance().getScheduler().scheduleDelayedTask(new ServerStopTask(getOwner()), 20*5);
	}
	
}
class ServerStopTask extends PluginTask<Main> {
	public ServerStopTask(Main owner) {
		super(owner);
	}

	@Override
	public void onRun(int currentTick) {
		for (Player player : Server.getInstance().getOnlinePlayers().values()) {
			for (int i = 0; i< 5; i++) {
				player.sendMessage(TextFormat.RED + "서버가 재부팅 됩니다.");
			}
		}
		Server.getInstance().shutdown();
	}
	
}