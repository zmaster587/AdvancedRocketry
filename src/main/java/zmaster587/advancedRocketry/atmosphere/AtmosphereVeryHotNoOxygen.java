package zmaster587.advancedRocketry.atmosphere;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import zmaster587.advancedRocketry.api.Configuration;
import zmaster587.advancedRocketry.network.PacketOxygenState;
import zmaster587.libVulpes.LibVulpes;
import zmaster587.libVulpes.network.PacketHandler;

public class AtmosphereVeryHotNoOxygen extends AtmosphereNeedsSuit {

	public static boolean enableNausea = Configuration.enableNausea;

	public AtmosphereVeryHotNoOxygen(boolean canTick, boolean isBreathable, boolean allowsCombustion,
									 String name) {
		super(canTick, isBreathable, allowsCombustion, name);
	}
	

	@Override
	public String getDisplayMessage() {
		return LibVulpes.proxy.getLocalizedString("msg.noOxygen");
	}
	
	// Needs full pressure suit
		protected boolean onlyNeedsMask()
		{
			return false;
		}
	
	@Override
	public void onTick(EntityLivingBase player) {
		if(player.worldObj.getTotalWorldTime() % 10  == 0 && !isImmune(player)) {
			player.attackEntityFrom(AtmosphereHandler.lowOxygenDamage, 1);
			if(player.worldObj.getTotalWorldTime() % 20 == 0) {
				player.attackEntityFrom(AtmosphereHandler.heatDamage, 1);
			}
			player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 40, 4));
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 40, 4));
			if(enableNausea) {
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 400, 1));
			}
			if(player instanceof EntityPlayer)
				PacketHandler.sendToPlayer(new PacketOxygenState(), (EntityPlayer)player);
		}
	}
}