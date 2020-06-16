**This project contains some API's I've created. Here are the list of API's and how to use it.**

- GUIApi (Easily make inventories)
- ItemStackBuilder (Easily create and modify itemstacks)
- ProfileApi (Create profiles for players, this requires MySQL or you can replace it with a flatfile)
- CooldownApi (Create multiple cooldowns for your players using 1 hash table)

**Here is how to use each one**

GUIApi:
Create inventories on the go: 

_Don't forget to implement the abstract methods_
```java
new GUI("name", 9).addItem(new ItemStack(Material.DIAMOND)).create();
```

Here is how to create an inventory and easily include listeners
```java
OnEnable:
guiManager = new GUIManager(this);
guiManager.addMenu("test", new TestGui("Test", 9));

On a different class:
public class TestGui extends GUI {

    public TestGui(String inventoryName, int size) {
        super(inventoryName, size);
        addItem(new ItemStackBuilder(new ItemStack(Material.SKULL_ITEM, 1, (byte) 3)).withOwner("Zxoir").withName("&aCustom &a&lSkull").withLore("&eCustom lore").withLore("test").build());
        inventoryDrag = false;
        inventoryClick = false;
    }

    @Override
    public void click(Player player, ItemStack itemStack, InventoryType inventoryType) {
        if (inventoryType.equals(InventoryType.CHEST)) {
            if (itemStack.getType().equals(Material.IRON_AXE)) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 10, 10);
                player.sendMessage("You have clicked an axe.");
            }
        }
    }
}
```

ItemStackBuilder API:
```java
new ItemStackBuilder(Material.GOLDEN_APPLE).withName("&aTest").withAmount(2).withLore("&aSeperate").withLore("lores").withLore("like").withLore("this").build();
```

Profile API:
```java
OnEnable:
profileManager = new ProfileManager();

OnJoin:
final Profile profile = new Profile(player);
profileManager.getProfiles().put(player.getUniqueId(), profile);
new ProfileLoader(profile, this).load();

OnQuit:
Profile profile = profileManager.getProfile(event.getPlayer());
new ProfileSaver(profile, this).save();
```

Cooldown API:

This is does not auto save on disable, you must implement that manually in case your saving method is different from mine
```java
setCooldown(player, "test", 20); // Sets a cooldown for 20 seconds
```



