package de.rapha149.cmdblockfinder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lang {

	public static String YES;
	public static String NO;
	
	public static String OVERWORLD;
	public static String NETHER;
	public static String END;
	
	public static String NORMAL_COMMANDBLOCK;
	public static String CHAIN_COMMANDBLOCK;
	public static String REPEATING_COMMANDBLOCK;

	public static String NOT_A_WORLD_FOLDER;
	public static String NO_REGION_FILES;
	public static String SHOW_WORLD_NAME;
	public static String SOME_FILES_COULD_NOT_BE_READ;

	public static String FIND_OR_REMOVE;
	public static String STATE_ONE_OR_TWO;

	public static String NO_COMMANDBLOCKS_FOUND;
	public static String COMMANDBLOCKS_FOUND;

	public static String DEFINE_WORLD;
	public static String WORLD_NOT_FOUND;
	public static String DEFINE_COORDS;
	public static String WRONG_COORDS_FORMAT;
	public static String NOT_A_COMMANDBLOCK;
	public static String COMMAND_ALREADY_EMPTY;
	public static String REMOVE_COMMANDBLOCK;
	public static String STATE_YES_OR_NO;
	public static String CANCELLED;
	public static String COMMANDBLOCK_REMOVED;

	public static void initialize(Language language) {
		switch (language) {
		case GERMAN:
			YES = "Ja";
			NO = "Nein";
			
			OVERWORLD = "Overworld";
			NETHER = "Nether";
			END = "Ende";

			NOT_A_WORLD_FOLDER = "Das ist kein Welt-Ordner.";
			NO_REGION_FILES = "Keine Regionen-Dateien gefunden.";
			SHOW_WORLD_NAME = "Welt gefunden: %s";
			SOME_FILES_COULD_NOT_BE_READ = "Manche Dateien konnten nicht ausgelesen werden.";

			FIND_OR_REMOVE = "Willst du Commandblöcke\n(1) suchen oder\n(2) entfernen?";
			STATE_ONE_OR_TWO = "Bitte die Zahl 1 oder 2 angeben.";

			NO_COMMANDBLOCKS_FOUND = "Keine Commandblöcke gefunden.";
			COMMANDBLOCKS_FOUND = "%s Commandblöcke wurden gefunden. Output-Datei: \"%s\"";
			
			DEFINE_WORLD = "Bitte die Welt angeben. (" + OVERWORLD + "/" + NETHER + "/" + END + ")";
			WORLD_NOT_FOUND = "Welt nicht gefunden.";
			DEFINE_COORDS = "Bitte gebe die Koordinaten des Commandblocks an. (Format: \"X Y Z\", Bsp: \"13 75 8\")";
			WRONG_COORDS_FORMAT = "Falsches Koordinaten-Format. (Format: \"X Y Z\", Bsp: \"13 75 8\")";
			NOT_A_COMMANDBLOCK = "An diesen Koordinaten ist kein Commandblock.";
			COMMAND_ALREADY_EMPTY = "Dieser Commandblock hat schon keinen Command.";
			REMOVE_COMMANDBLOCK = "Willst du den Command des Commandblocks an %s / %s %s %s wirklich entfernen? (" + YES
					+ "/" + NO + ")\nSein Command: %s";
			STATE_YES_OR_NO = "Bitte " + YES + " oder " + NO + " angeben.";
			CANCELLED = "Abgebrochen.";
			COMMANDBLOCK_REMOVED = "Der Command des Commandblocks wurde entfernt.";
			break;
		case ENGLISH:
			YES = "Yes";
			NO = "No";
			
			OVERWORLD = "Overworld";
			NETHER = "Nether";
			END = "End";

			NOT_A_WORLD_FOLDER = "This is not a world folder.";
			NO_REGION_FILES = "No region files found.";
			SHOW_WORLD_NAME = "World found: %s";
			SOME_FILES_COULD_NOT_BE_READ = "Some files could not be read.";

			FIND_OR_REMOVE = "Do you want to\n(1) find or\n(2) remove\nCommandblocks?";
			STATE_ONE_OR_TWO = "Please state the number 1 or 2.";

			NO_COMMANDBLOCKS_FOUND = "No commandblocks found.";
			COMMANDBLOCKS_FOUND = "%s commandblocks found. Output file: \"%s\"";
			
			DEFINE_WORLD = "Please define the world. (" + OVERWORLD + "/" + NETHER + "/" + END + ")";
			WORLD_NOT_FOUND = "World not found.";
			DEFINE_COORDS = "Please define the coordinates of the commandblock. (Format: \"X Y Z\", e.g: \"13 75 8\")";
			WRONG_COORDS_FORMAT = "Wrong coordinate format. (Format: \"X Y Z\", e.g: \"13 75 8\")";
			NOT_A_COMMANDBLOCK = "There is no commandblock at these coordinates.";
			COMMAND_ALREADY_EMPTY = "This commandblock already has no command.";
			REMOVE_COMMANDBLOCK = "Do you want to remove the command of the commandblock at %s / %s %s %s? (" + YES
					+ "/" + NO + ")\nIts Command: %s";
			STATE_YES_OR_NO = "State " + YES + " or " + NO + ".";
			CANCELLED = "Cancelled.";
			COMMANDBLOCK_REMOVED = "The command of the commandblock was removed.";
			break;
		}
	}

	public static enum Language {
		GERMAN("German", "de", "ger", "german"), ENGLISH("English", "en", "english");

		private String name;
		private List<String> identifiers;

		private Language(String name, String... identifiers) {
			this.name = name;
			this.identifiers = Arrays.asList(identifiers);
		}

		public static Language of(String identifier) {
			for (Language language : values())
				if (language.identifiers.contains(identifier.toLowerCase()))
					return language;
			return null;
		}

		public static Map<String, List<String>> getPossibleIdentifiers() {
			Map<String, List<String>> identifiers = new HashMap<>();
			for (Language language : values())
				identifiers.put(language.name, language.identifiers);
			return identifiers;
		}
	}

}
