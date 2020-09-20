package de.rapha149.cmdblockfinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.rapha149.cmdblockfinder.Lang.Language;
import net.querz.mca.Chunk;
import net.querz.mca.LoadFlags;
import net.querz.mca.MCAFile;
import net.querz.mca.MCAUtil;
import net.querz.nbt.tag.CompoundTag;

public class CommandblockFinder {

	private static FileFilter fileFilter = file -> file.getName().endsWith(".mca");
	private static List<String> ids = Arrays.asList("minecraft:command_block", "minecraft:chain_command_block",
			"minecraft:repeating_command_block");

	private static boolean overworld;
	private static boolean nether;
	private static boolean end;

	private static int fileCount;
	private static int lastPercent = -1;
	private static String lastLine;

	public static void main(String[] args) {
		Language language = Language.ENGLISH;
		if (args.length > 0) {
			language = Language.of(args[0]);
			if (language == null) {
				StringBuilder sb = new StringBuilder();
				Language.getPossibleIdentifiers().forEach(
						(name, identifiers) -> sb.append("\n- " + name + ": " + String.join(", ", identifiers)));

				System.err
						.println("Unknown language identifier: " + args[0] + "\nPossible language identifiers: " + sb);
				System.exit(1);
				return;
			}
		}
		Lang.initialize(language);

		if (new File("level.dat").exists()) {
			File current;
			if ((current = new File("region")).exists() && current.isDirectory()
					&& current.listFiles(fileFilter).length > 0)
				overworld = true;
			if ((current = new File("Dim-1/region")).exists() && current.isDirectory()
					&& current.listFiles(fileFilter).length > 0)
				nether = true;
			if ((current = new File("Dim1/region")).exists() && current.isDirectory()
					&& current.listFiles(fileFilter).length > 0)
				end = true;

			if (!overworld && !nether && !end)
				System.out.println(Lang.NO_REGION_FILES);
			else {
				try {
					System.out.println(Lang.FIND_OR_REMOVE);
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					while (true) {
						try {
							int answer = Integer.parseInt(br.readLine());

							if (answer == 1)
								find();
							else if (answer == 2) {
								System.out.println(Lang.DEFINE_WORLD);
								while (true) {
									String world = br.readLine();
									if (getRegionFolder(world) != null) {
										System.out.println(Lang.DEFINE_COORDS);
										while (true) {
											String[] coords = br.readLine().split(" ");
											try {
												if (coords.length == 3) {
													int x = Integer.parseInt(coords[0]);
													int y = Integer.parseInt(coords[1]);
													int z = Integer.parseInt(coords[2]);
													remove(br, world, x, y, z);
													break;
												} else
													System.out.println(Lang.WRONG_COORDS_FORMAT);
											} catch (NumberFormatException e) {
												System.out.println(Lang.WRONG_COORDS_FORMAT);
											}
										}
										break;
									} else
										System.out.println(Lang.WORLD_NOT_FOUND);
								}
							} else {
								System.out.println(Lang.STATE_ONE_OR_TWO);
								continue;
							}

							System.exit(0);
							break;
						} catch (NumberFormatException e) {
							System.out.println(Lang.STATE_ONE_OR_TWO);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else
			System.out.println(Lang.NOT_A_WORLD_FOLDER);
	}

	private static void find() throws IOException {
		List<Commandblock> commandblocks = new ArrayList<>();
		Map<String, File[]> worlds = new HashMap<>();
		if (overworld)
			worlds.put(Lang.OVERWORLD, new File(getRegionFolder(Lang.OVERWORLD)).listFiles(fileFilter));
		if (nether)
			worlds.put(Lang.NETHER, new File(getRegionFolder(Lang.NETHER)).listFiles(fileFilter));
		if (end)
			worlds.put(Lang.END, new File(getRegionFolder(Lang.END)).listFiles(fileFilter));

		int length = 0;
		for (File[] f : worlds.values())
			length += f.length;
		fileCount = length * 32 * 32;
		int count = 0;
		for (Entry<String, File[]> entry : worlds.entrySet()) {
			File[] files = entry.getValue();
			for (int i = 0; i < files.length; i++) {
				try {
					MCAFile file = MCAUtil.read(files[i], LoadFlags.TILE_ENTITIES);
					for (int j = 0; j < 32 * 32; j++) {
						count++;
						calcPercent(count);

						Chunk chunk = file.getChunk(j);
						if (chunk != null && chunk.getTileEntities() != null) {
							chunk.getTileEntities().forEach(tileEntity -> {
								if (ids.contains(tileEntity.getString("id"))) {
									commandblocks.add(new Commandblock(entry.getKey(), tileEntity.getInt("x"),
											tileEntity.getInt("y"), tileEntity.getInt("z"),
											tileEntity.getString("Command")));
								}
							});
						}
					}
				} catch (ClassCastException e) {
					continue;
				}
			}
		}

		calcPercent(fileCount);

		if (!commandblocks.isEmpty()) {
			commandblocks.sort((c1, c2) -> {
				int dx = c1.x - c2.x;
				int dy = c1.y - c2.y;
				int dz = c1.z - c2.z;

				if (dx != 0)
					return dx;
				if (dz != 0)
					return dz;
				return dy;
			});

			File file = new File(
					new SimpleDateFormat("'CommandblockFinder_'yyyy-MM-dd_HH.mm.ss'.txt'").format(new Date()));
			FileWriter writer = new FileWriter(file);
			for (Commandblock block : commandblocks)
				writer.write(block.toString() + "\n");
			writer.flush();
			writer.close();

			System.out.printf(Lang.COMMANDBLOCKS_FOUND, commandblocks.size(), file.getName()).println();
		} else
			System.out.println(Lang.NO_COMMANDBLOCKS_FOUND);
	}

	private static void remove(BufferedReader br, String world, int x, int y, int z) throws IOException {
		File[] files = new File(getRegionFolder(world)).listFiles(file -> file.getName().endsWith(".mca"));
		fileCount = files.length * 32 * 32;
		int count = 0;
		for (int i = 0; i < files.length; i++) {
			try {
				MCAFile file = MCAUtil.read(files[i]);
				for (int j = 0; j < 32 * 32; j++) {
					count++;
					calcPercent(count);
					Chunk chunk = file.getChunk(j);

					if (chunk != null) {
						for (CompoundTag tileEntity : chunk.getTileEntities()) {
							if (tileEntity.getInt("x") == x && tileEntity.getInt("y") == y
									&& tileEntity.getInt("z") == z) {
								calcPercent(fileCount);
								if (ids.contains(tileEntity.getString("id"))) {
									String command = tileEntity.getString("Command").trim();

									if (!command.isEmpty()) {
										System.out.printf(Lang.REMOVE_COMMANDBLOCK, world, x, y, z, command).println();
										while (true) {
											String line = br.readLine();
											if (line.equalsIgnoreCase(Lang.YES)) {
												tileEntity.putString("Command", "");
												MCAUtil.write(file, files[i], true);
												System.out.println(Lang.COMMANDBLOCK_REMOVED);
												return;
											}

											else if (line.equalsIgnoreCase(Lang.NO)) {
												System.out.println(Lang.CANCELLED);
												return;
											}

											else
												System.out.println(Lang.STATE_YES_OR_NO);
										}
									} else {
										System.out.println(Lang.COMMAND_ALREADY_EMPTY);
										return;
									}
								} else {
									System.out.println(Lang.NOT_A_COMMANDBLOCK);
									return;
								}
							}
						}
					}
				}
			} catch (ClassCastException e) {
				continue;
			}
		}

		System.out.println(Lang.NOT_A_COMMANDBLOCK);
	}

	private static void calcPercent(int completed) {
		int percent = Math.round((float) completed / (float) fileCount * 100f);
		if (percent != lastPercent) {
			if (lastLine != null)
				for (int i = 0; i < lastLine.length(); i++)
					System.out.print("\b \b");

			StringBuilder sb = new StringBuilder("[");
			for (int i = 0; i < percent; i++)
				sb.append("#");
			for (int i = 0; i < 100 - percent; i++)
				sb.append(".");
			sb.append("]");

			System.out.print(lastLine = percent + " %  " + sb + (percent == 100 ? "\n" : ""));
			lastPercent = percent;
		}
	}

	private static String getRegionFolder(String worldName) {
		if (worldName.equalsIgnoreCase(Lang.OVERWORLD))
			return "region";
		else if (worldName.equalsIgnoreCase(Lang.NETHER))
			return "Dim-1/region";
		else if (worldName.equalsIgnoreCase(Lang.END))
			return "Dim1/region";
		else
			return null;
	}

	private static class Commandblock {

		String world;
		int x;
		int y;
		int z;
		String command;

		Commandblock(String world, int x, int y, int z, String command) {
			this.world = world;
			this.x = x;
			this.y = y;
			this.z = z;
			this.command = command.substring(command.startsWith("/") ? 1 : 0);
		}

		@Override
		public String toString() {
			return world + " / " + x + " " + y + " " + z + ": " + command + "\n";
		}
	}

}
