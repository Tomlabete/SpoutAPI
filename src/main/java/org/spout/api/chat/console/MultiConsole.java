/*
 * This file is part of SpoutAPI.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * SpoutAPI is licensed under the SpoutDev License Version 1.
 *
 * SpoutAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * SpoutAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
/*
 * This file is part of Spout.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * Spout is licensed under the SpoutDev License Version 1.
 *
 * Spout is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * Spout is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.api.chat.console;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.spout.api.chat.ChatArguments;

/**
 * A wrapper console that allows accessing multiple consoles at once
 */
public class MultiConsole implements Console {
	private final List<Console> consoles = new ArrayList<Console>();

	public MultiConsole() {
	}

	public MultiConsole(Console... consoles) {
		this.consoles.addAll(Arrays.asList(consoles));
	}

	/**
	 * Returns the list of consoles managed by this MultiConsole
	 *
	 * @return The managed consoles
	 */
	public List<Console> getConsoles() {
		return Collections.unmodifiableList(consoles);
	}

	/**
	 * Removes a console from management
	 * Remember that this will not close the console. The close method must be called manually.
	 *
	 * @param console The console to remove
	 * @return Whether the console was removed (previously present)
	 */
	public boolean removeConsole(Console console) {
		return consoles.remove(console);
	}

	/**
	 * This method will add a console to management by this MultiConsole.
	 *
	 * @param console The console to add
	 */
	public void addConsole(Console console) {
		consoles.add(console);
		console.init();
	}

	@Override
	public void init() {
		for (Console console : consoles) {
			console.init();
		}
	}

	@Override
	public boolean isInitialized() {
		for (Console console : consoles) {
			if (console.isInitialized()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void close() {
		for (Console console : consoles) {
			console.close();
		}
	}

	@Override
	public void setDateFormat(DateFormat format) {
		for (Console console : consoles) {
			console.setDateFormat(format);
		}
	}

	@Override
	public void addMessage(ChatArguments message) {
		for (Console console : consoles) {
			console.addMessage(message);
		}
	}
}
