package com.tyler.narutoorigin.gui;

import com.tyler.narutoorigin.coremod.CommandClaimOrigin;
import com.tyler.narutoorigin.coremod.CommandSimulate;
import com.tyler.narutoorigin.coremod.PlayerData;
import com.tyler.narutoorigin.coremod.PlayerDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GuiOriginSelection extends GuiScreen {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("narutoorigin", "textures/gui/naruto_gui_background_512.png");
    private final String[] originResult;
    private final EntityPlayerMP player;
    private final CommandSimulate commandSimulate;
    private final PlayerDataManager playerDataManager;
    private final String clan;

    private static final Map<String, ResourceLocation> ICONS = new HashMap<>();
    private static final Map<String, int[]> ICON_SIZES = new HashMap<>();
    private static final Map<String, String[]> KKG_TEXTS = new HashMap<>();
    private static final Map<String, String[]> DOJUTSU_TEXTS = new HashMap<>();
    private static final Map<String, String[]> ELEMENT_TEXTS = new HashMap<>();
    private static final Map<String, String[]> MEDIC_TEXTS = new HashMap<>();
    private static final Map<String, String[]> TAILED_BEAST_TEXTS = new HashMap<>();
    private static final Map<String, String[]> TAIJUTSU_TEXTS = new HashMap<>();
    private final Random random = new Random();

    private String selectedKKGText;
    private String selectedDojutsuText;
    private String selectedElementText;
    private String selectedMedicText;
    private String selectedTailedBeastText;
    private String selectedTaijutsuText;

    static {
        // Icons für Dojutsu
        ICONS.put("Sharingan", new ResourceLocation("narutoorigin", "textures/icons/sharingan.png"));
        ICON_SIZES.put("Sharingan", new int[]{64, 64});

        ICONS.put("Byakugan", new ResourceLocation("narutoorigin", "textures/icons/byakugan.png"));
        ICON_SIZES.put("Byakugan", new int[]{64, 64});

        ICONS.put("Rinnegan", new ResourceLocation("narutoorigin", "textures/icons/rinnegan.png"));
        ICON_SIZES.put("Rinnegan", new int[]{64, 64});

      // Beispieltexte für KKGs ohne "Release"
KKG_TEXTS.put("Boil", new String[]{
    "Master of Boil, a volatile combination of water and fire. Known for unleashing scalding steam that melts armor and flesh alike. Approach with extreme caution, as this shinobis boiling fury is relentless.",
    "This shinobi commands Boil, turning the battlefield into a blistering inferno. Victims often succumb to severe burns and suffocation. Handle with care steaming death awaits those who underestimate them.",
    "A wielder of Boil, infamous for leaving only charred remains in their wake. Engages with high pressure steam capable of overwhelming any defense. Do not engage directly target from a distance if possible."
});
KKG_TEXTS.put("Explosion", new String[]{
    "Shinobi with the explosive power of Explosion. Can mold chakra into detonating clay, causing massive destruction. Known to reduce enemies to dust engage at your own peril.",
    "Possesses the rare Explosion, manipulating earth and lightning to deadly effect. Capable of obliterating targets in an instant. Approach with extreme caution; detonation is imminent.",
    "This shinobis Explosion is notorious for leaving nothing but craters. Their ability to craft explosive clay makes them a walking bomb. Engage only with utmost caution and precision."
});
KKG_TEXTS.put("Ice", new String[]{
    "Ice user, able to freeze opponents solid and create lethal ice constructs. Known for leaving battlefields covered in a deadly frost. Considered highly dangerous engage only with heat based attacks.",
    "Wields the chilling power of Ice, freezing enemies in their tracks. Encounters often end with victims trapped in ice, never to thaw. Avoid direct confrontation unless properly equipped.",
    "This shinobi commands Ice, turning the tide of battle with freezing barrages. Encounters have resulted in entire squads encased in ice. Only engage if you can withstand sub zero conditions."
});
KKG_TEXTS.put("Lava", new String[]{
    "Lava user, capable of spewing molten rock with devastating effect. Known for incinerating everything in their path, leaving only scorched earth behind. Engage with extreme caution approach from high ground if possible.",
    "Possesses the destructive Lava, a deadly fusion of fire and earth. Victims are often found buried under molten lava. Do not engage in close quarters maintain distance at all costs.",
    "A shinobi feared for their mastery of Lava, melting both terrain and enemies alike. Engaging this target without preparation is suicide. Approach only with extreme heat resistance."
});
KKG_TEXTS.put("Magnet", new String[]{
    "Master of Magnet, able to manipulate metallic substances with deadly precision. Known for weaponizing metal sands and crushing enemies with magnetic force. Engage with non metallic weapons avoid at all costs.",
    "This shinobi wields Magnet, a powerful ability to control ferrous materials. Encounters often end with crushed bodies and collapsed structures. Do not carry metal near this target.",
    "Feared for their use of Magnet, turning the battlefield into a death trap of metallic shards. Engage only with ceramic or wooden weapons metal will be your undoing."
});
KKG_TEXTS.put("Scorch", new String[]{
    "Scorch user, capable of evaporating moisture from the body with scorching heat. Victims are often found desiccated and charred. Approach with extreme caution cooling equipment recommended.",
    "This shinobi commands Scorch, reducing enemies to nothing but ashes. Encounters are brief and fatal direct confrontation is ill advised. Stay out of range and use cooling jutsu.",
    "Wielder of the devastating Scorch, known for burning enemies alive. Approach with heat dampening measures or risk instant incineration. Engage only from a safe distance."
});
KKG_TEXTS.put("Storm", new String[]{
    "Storm user, combining lightning and water to create deadly storms. Known for striking enemies with pinpoint accuracy using charged water. Approach with caution electrically insulated armor recommended.",
    "Possesses the unique Storm, unleashing torrents of charged water that paralyze and destroy. Encounters often end in electrocution. Engage only with proper insulation.",
    "Feared for their mastery of Storm, bringing thunderstorms to the battlefield. Engaging this shinobi without electrical resistance is suicide. Avoid waterlogged areas during confrontation."
});
KKG_TEXTS.put("Dust", new String[]{
    "Shinobi with the rare Dust, capable of disintegrating matter at an atomic level. Known for erasing targets from existence in mere seconds. Approach with extreme caution avoid direct line of sight.",
    "Wielder of Dust, a Kekkei Tota that reduces anything in its path to dust. Victims are completely disintegrated engage from behind cover if at all. Absolute precision is required.",
    "This shinobi commands Dust, capable of vaporizing enemies instantly. Engage only with extreme caution stay out of sight and range. Direct confrontation is not recommended."
});
KKG_TEXTS.put("shikotsumyaku", new String[]{
    "A deadly member of the Kaguya Clan with the ability to manipulate their bones into lethal weapons. Known for impaling enemies with sharpened bone spears. This shinobis bones are harder than steel.",
    "Possessor of the Shikotsumyaku, capable of growing bone weapons from within their body. Victims are often found pierced by these deadly bones. Engage only with specialized armor avoid close combat at all costs.",
    "Feared Shikotsumyaku user, turning their own skeleton into a weapon of destruction. This shinobis body is both sword and shield. Approach with caution, and be prepared for a fight to the death."
});
KKG_TEXTS.put("Wood", new String[]{
    "The legendary Wood, combining Earth and Water, allows the user to bring life to the battlefield, creating forests and powerful constructs. Engage with caution, for the very terrain may rise against you.",
    "Wielding the Wood, a rare and potent ability, the user can regenerate quickly and create forested battlegrounds, ensnaring foes with living wood and overwhelming them with natures fury.",
    "This shinobi possesses the revered Wood, capable of converting chakra into life itself. Forests grow at their command, and their constructs are capable of rivaling even Tailed Beasts. Be prepared for a fight against nature incarnate."
});

// Beispieltexte für Dojutsus
DOJUTSU_TEXTS.put("Sharingan", new String[]{
    "Grants unparalleled perception in battle. Can predict opponents movements and copy jutsu. Wielder of the Sharingan can cast powerful genjutsu.",
    "This shinobis Sharingan can predict your moves and trap you in illusions. Avoid eye contact at all costs engage with long range attacks only.",
    "Wielder of the Sharingan, notorious for their ability to copy jutsu and see through deception. Approach with extreme caution close combat is ill advised."
});
DOJUTSU_TEXTS.put("Byakugan", new String[]{
    "Grants 360 degree vision and sees chakra points. Allows for surgical strikes with precision. Unparalleled sensory perception in battle.",
    "This shinobis Byakugan grants them 360 degree vision and the ability to incapacitate foes with a single touch. Avoid close combat engage from a distance.",
    "Wielder of the Byakugan, able to see through walls and attack with deadly precision. Approach with stealth or risk being detected and neutralized instantly."
});
DOJUTSU_TEXTS.put("Rinnegan", new String[]{
    "Said to wield the power of the gods. Capable of mastering all forms of chakra and manipulating life and death. Engage with your most caution approach only if you possess similar power.",
    "This shinobis Rinnegan grants them unparalleled abilities, including resurrection and space time manipulation. Avoid direct confrontation engage only if absolutely necessary.",
    "Feared for their Rinnegan, a Dojutsu that controls life itself. Approach with extreme caution prepare for any and all possibilities. Direct engagement is likely fatal."
});

// Beispieltexte für Elemente
ELEMENT_TEXTS.put("fire", new String[]{
    "Master of Fire, reducing everything to ashes. Use water jutsu to counter.",
    "This shinobi wields Fire, capable of turning the battlefield into a raging inferno. Approach with extreme caution and heat resistant gear.",
    "Possessor of Fire, feared for their ability to reduce foes to ashes. Engage with water based jutsu to counter their flames."
});
ELEMENT_TEXTS.put("wind", new String[]{
    "Master of Wind, slicing through armor. Engage from a distance.",
    "This shinobi controls Wind, using it to deliver deadly, razor sharp attacks. Approach with caution and avoid aerial combat.",
    "Possessor of Wind, known for their swift, slicing attacks. Engage with earth based jutsu to counter their mobility."
});
ELEMENT_TEXTS.put("water", new String[]{
    "Master of Water, creating waves. Engage from high ground.",
    "This shinobi commands Water, turning the battlefield into a torrential flood. Approach from high ground to avoid being swept away.",
    "Possessor of Water, feared for their ability to drown enemies in a deluge. Engage with lightning based jutsu to counter their waves."
});
ELEMENT_TEXTS.put("earth", new String[]{
    "Master of Earth, creating barriers. Engage from above.",
    "This shinobi wields Earth, using it to create unbreakable barriers and launch devastating attacks. Approach with caution and avoid direct confrontation.",
    "Possessor of Earth, known for their impenetrable defenses. Engage with wind based jutsu to erode their barriers."
});
ELEMENT_TEXTS.put("lightning", new String[]{
    "Master of Lightning, delivering fatal shocks. Insulation is crucial.",
    "This shinobi wields Lightning, delivering high voltage attacks that can paralyze foes instantly. Approach with caution and avoid metal weapons.",
    "Possessor of Lightning, feared for their ability to incapacitate opponents with a single strike. Engage with earth based jutsu to ground their power."
});

// Beispieltexte für Medic
MEDIC_TEXTS.put("medic", new String[]{
    "A master of medical jutsu, able to turn the tide of battle by healing. Strike fast.",
    "This shinobi is a skilled medic, proficient in healing jutsu. Approach with caution.",
    "Capable of mending wounds in the heat of battle. Dont give them the chance."
});

// Beispieltexte für Tailed Beasts
TAILED_BEAST_TEXTS.put("Shukaku", new String[]{
    "Wielder of Shukakus power, capable of creating massive sandstorms. Engage from a distance and avoid desert areas.",
    "This shinobi is a jinchuriki of Shukaku, wielding the destructive power of sand. Approach with caution and avoid being buried alive.",
    "Possesses the power of Shukaku, the One Tail. Known for its sand based abilities. Engage with caution avoid direct confrontation."
});
TAILED_BEAST_TEXTS.put("Matatabi", new String[]{
    "Wielder of Matatabis power, capable of unleashing blue flames. Engage from a distance and avoid direct contact.",
    "This shinobi is a jinchuriki of Matatabi, wielding blue flames that can incinerate anything. Approach with caution and use water based jutsu.",
    "Possesses the power of Matatabi, the Two Tails. Known for its fiery abilities. Engage with caution avoid close combat."
});
TAILED_BEAST_TEXTS.put("Isobu", new String[]{
    "Wielder of Isobus power, capable of summoning tidal waves. Engage from high ground and avoid waterlogged areas.",
    "This shinobi is a jinchuriki of Isobu, controlling the power of the sea. Approach with caution and avoid being dragged underwater.",
    "Possesses the power of Isobu, the Three Tails. Known for its water based abilities. Engage with caution avoid underwater combat."
});
TAILED_BEAST_TEXTS.put("Son Goku", new String[]{
    "Wielder of Son Gokus power, capable of creating rivers of lava. Engage from a distance and avoid volcanic regions.",
    "This shinobi is a jinchuriki of Son Goku, wielding molten lava that can melt anything. Approach with caution and use ice based jutsu.",
    "Possesses the power of Son Goku, the Four Tails. Known for its lava based abilities. Engage with caution avoid volcanic areas."
});
TAILED_BEAST_TEXTS.put("Kokuo", new String[]{
    "Wielder of Kokuos power, capable of creating steam explosions. Engage from a distance and avoid enclosed spaces.",
    "This shinobi is a jinchuriki of Kokuo, capable of generating high pressure steam. Approach with caution and avoid close combat.",
    "Possesses the power of Kokuo, the Five Tails. Known for its steam based abilities. Engage with caution avoid direct confrontation."
});
TAILED_BEAST_TEXTS.put("Saiken", new String[]{
    "Wielder of Saikens power, capable of creating acidic mist. Engage from a distance and avoid areas covered in slime.",
    "This shinobi is a jinchuriki of Saiken, capable of secreting corrosive substances. Approach with caution and avoid close combat.",
    "Possesses the power of Saiken, the Six Tails. Known for its corrosive abilities. Engage with caution avoid contact with its slime."
});
TAILED_BEAST_TEXTS.put("Chomei", new String[]{
    "Wielder of Chomeis power, capable of blinding opponents and attacking from above. Engage from the ground and avoid open areas.",
    "This shinobi is a jinchuriki of Chomei, capable of flight and generating blinding powder. Approach with caution and avoid the skies.",
    "Possesses the power of Chomei, the Seven Tails. Known for its flight abilities. Engage with caution avoid aerial combat."
});
TAILED_BEAST_TEXTS.put("Gyuki", new String[]{
    "Wielder of Gyukis power, capable of drowning foes in ink. Engage from a distance and avoid being blinded.",
    "This shinobi is a jinchuriki of Gyuki, capable of generating powerful ink clouds. Approach with caution and avoid close combat.",
    "Possesses the power of Gyuki, the Eight Tails. Known for its ink based abilities. Engage with caution avoid getting caught in its ink."
});
TAILED_BEAST_TEXTS.put("Kurama", new String[]{
    "Wielder of Kuramas power, capable of devastating entire landscapes. Engage from a distance and avoid direct combat.",
    "This shinobi is a jinchuriki of Kurama, wielding overwhelming power. Approach with caution and avoid being overwhelmed by its chakra.",
    "Possesses the power of Kurama, the Nine Tails. Known for its immense strength and chakra. Engage with extreme caution avoid direct confrontation."
});

// Beispieltexte für Taijutsu
TAIJUTSU_TEXTS.put("8 Gates", new String[]{
    "This shinobi wields the forbidden power of the Eight Gates, pushing their body beyond human limits. Their strikes can shatter bones and crack the earth itself. Do not engage in close combat retreat is the only option when they open the gates.",
    "A master of the Eight Gates, capable of overwhelming even the strongest defenses with sheer physical force. They have survived opening the Seventh Gate, turning themselves into a human wrecking ball. Keep your distance at all costs.",
    "Known to unleash the Eight Gates, this shinobi becomes an unstoppable force, sacrificing their body for unimaginable power. Their blows are lethal and inescapable engage only if you wish to meet certain death."
});

    }

    public GuiOriginSelection(EntityPlayerMP player, String[] originResult, CommandSimulate commandSimulate, PlayerDataManager playerDataManager) {
        this.player = player;
        this.originResult = originResult != null ? originResult : new String[7]; // Fallback zu einem leeren Array
        this.commandSimulate = commandSimulate;
        this.playerDataManager = playerDataManager;
        this.clan = originResult.length > 0 ? originResult[0] : "";

        // Texte für KKG, Dojutsu usw. einmalig auswählen
        String kkg = originResult.length > 3 ? originResult[3] : null;
        if (kkg != null && KKG_TEXTS.containsKey(kkg)) {
            this.selectedKKGText = KKG_TEXTS.get(kkg)[random.nextInt(KKG_TEXTS.get(kkg).length)];
        }

        String dojutsu = originResult.length > 1 ? originResult[1] : null;
        if (dojutsu != null && DOJUTSU_TEXTS.containsKey(dojutsu)) {
            this.selectedDojutsuText = DOJUTSU_TEXTS.get(dojutsu)[random.nextInt(DOJUTSU_TEXTS.get(dojutsu).length)];
        }

        String element = originResult.length > 2 ? originResult[2] : null;
        if (element != null && ELEMENT_TEXTS.containsKey(element.toLowerCase())) {
            this.selectedElementText = ELEMENT_TEXTS.get(element.toLowerCase())[random.nextInt(ELEMENT_TEXTS.get(element.toLowerCase()).length)];
        }

        String medic = originResult.length > 5 ? originResult[5] : null;
        if (medic != null && medic.equalsIgnoreCase("medic")) {
            this.selectedMedicText = MEDIC_TEXTS.get("medic")[random.nextInt(MEDIC_TEXTS.get("medic").length)];
        }

        String tailedBeast = originResult.length > 6 ? originResult[6] : null;
        if (tailedBeast != null && TAILED_BEAST_TEXTS.containsKey(tailedBeast)) {
            this.selectedTailedBeastText = TAILED_BEAST_TEXTS.get(tailedBeast)[random.nextInt(TAILED_BEAST_TEXTS.get(tailedBeast).length)];
        }

        String taijutsu = originResult.length > 4 ? originResult[4] : null;
        if (taijutsu != null && TAIJUTSU_TEXTS.containsKey(taijutsu)) {
            this.selectedTaijutsuText = TAIJUTSU_TEXTS.get(taijutsu)[random.nextInt(TAIJUTSU_TEXTS.get(taijutsu).length)];
        }
    }

    @Override
    public void initGui() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        GuiButton claimButton = new GuiButton(0, centerX - 150, centerY + 100, 90, 20, "Claim");
        this.buttonList.add(claimButton);

        // Sichtbarkeit des Mauszeigers aktivieren
        Mouse.setGrabbed(false);
    }

@Override
public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    // Ensure the mouse pointer is visible
    Mouse.setGrabbed(false);

    // Check that the necessary objects are not null
    if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getTextureManager() == null || this.player == null || this.fontRenderer == null) {
        return;
    }

    Minecraft.getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);

    int textureWidth = 512;
    int textureHeight = 512;
    int centerX = (this.width - textureWidth) / 2;
    int centerY = (this.height - textureHeight) / 2;

    drawModalRectWithCustomSizedTexture(centerX, centerY, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);

    // The target X-coordinate for centering the text
    int targetX = 575;

    // Calculate the starting X-coordinate to center the text
    int nameWidth = this.fontRenderer.getStringWidth(this.player.getName());
    int clanWidth = this.fontRenderer.getStringWidth(this.clan);

    int nameX = targetX - (nameWidth / 2);
    int clanX = targetX - (clanWidth / 2);

    // Draw the name and clan strings centered at the target X-coordinate
    this.fontRenderer.drawString(this.player.getName(), nameX, centerY + 31, 0xFFFFFF);
    this.fontRenderer.drawString(this.clan, clanX, centerY + 68, 0xFFFFFF);

    int kkgTaijutsuYOffset = 100;
    if (selectedTaijutsuText != null) {
        this.fontRenderer.drawSplitString(selectedTaijutsuText, centerX + 174, centerY + kkgTaijutsuYOffset, 150, 0xFFFFFF);
    } else if (selectedKKGText != null) {
        this.fontRenderer.drawSplitString(selectedKKGText, centerX + 174, centerY + kkgTaijutsuYOffset, 150, 0xFFFFFF);
    }

    int dojutsuYOffset = 180;
    if (selectedDojutsuText != null) {
        this.fontRenderer.drawSplitString(selectedDojutsuText, centerX + 174, centerY + dojutsuYOffset, 158, 0xFFFFFF);
        drawStaticIcon(originResult.length > 1 ? originResult[1] : "", centerX + 65, centerY + 306);
    }

    int elementYOffset = 275;
    if (selectedElementText != null) {
        this.fontRenderer.drawSplitString(selectedElementText, centerX + 174, centerY + elementYOffset, 150, 0xFFFFFF);
    }

    int medicYOffset = 330;
    if (selectedMedicText != null) {
        this.fontRenderer.drawSplitString(selectedMedicText, centerX + 174, centerY + medicYOffset, 158, 0xFFFFFF);
    }

    int tailedBeastYOffset = 370;
    if (selectedTailedBeastText != null) {
        this.fontRenderer.drawSplitString(selectedTailedBeastText, centerX + 174, centerY + tailedBeastYOffset, 150, 0xFFFFFF);
    }

    GuiButton claimButton = this.buttonList.get(0);
    claimButton.x = centerX + 53;
    claimButton.y = centerY + 207;

    super.drawScreen(mouseX, mouseY, partialTicks);
}



    private void drawStaticIcon(String key, int x, int y) {
        if (ICONS.containsKey(key) && ICON_SIZES.containsKey(key)) {
            mc.getTextureManager().bindTexture(ICONS.get(key));
            int[] size = ICON_SIZES.get(key);

            GlStateManager.pushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            drawModalRectWithCustomSizedTexture(x, y, 0, 0, size[0], size[1], size[0], size[1]);

            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) { // Claim Button clicked
            if (player == null || playerDataManager == null || commandSimulate == null || originResult == null) {
                return; // Sicherstellen, dass player, playerDataManager, commandSimulate und originResult nicht null sind
            }

            String playerName = player.getName();
            String worldName = player.getServerWorld().getWorldInfo().getWorldName();
            PlayerData playerData = playerDataManager.getPlayerData(playerName, worldName);

            if (playerData.hasClaimedOrigin()) {
                player.sendMessage(new TextComponentString(TextFormatting.RED + "You have already claimed an Origin in this world."));
                return;
            }

            CommandClaimOrigin.claimOrigin(player, originResult, playerDataManager, commandSimulate);

            // Execute /resetMe command
            Minecraft.getMinecraft().player.sendChatMessage("/resetMe");

            // Close the GUI after a short delay
            Minecraft.getMinecraft().addScheduledTask(() -> {
                this.mc.displayGuiScreen(null);
                if (this.mc.currentScreen == null) {
                    this.mc.setIngameFocus();
                }
            });

            // Execute /makeMeAShinobi command after another short delay
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Minecraft.getMinecraft().player.sendChatMessage("/makeMeAShinobi");
            });
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }
}
