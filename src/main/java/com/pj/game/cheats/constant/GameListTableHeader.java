package com.pj.game.cheats.constant;

public enum GameListTableHeader {
	S_N("S No"), TYPE("Type"), NAME("Name");

	private String value;

	GameListTableHeader(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return this.getValue();
	}

	public static GameListTableHeader getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (GameListTableHeader v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}
}
