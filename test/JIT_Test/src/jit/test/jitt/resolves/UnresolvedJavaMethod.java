/*******************************************************************************
 * Copyright (c) 2001, 2017 IBM Corp. and others
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License 2.0 which accompanies this
 * distribution and is available at https://www.eclipse.org/legal/epl-2.0/
 * or the Apache License, Version 2.0 which accompanies this distribution and
 * is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following
 * Secondary Licenses when the conditions for such availability set
 * forth in the Eclipse Public License, v. 2.0 are satisfied: GNU
 * General Public License, version 2 with the GNU Classpath
 * Exception [1] and GNU General Public License, version 2 with the
 * OpenJDK Assembly Exception [2].
 *
 * [1] https://www.gnu.org/software/classpath/license.html
 * [2] http://openjdk.java.net/legal/assembly-exception.html
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 *******************************************************************************/
package jit.test.jitt.resolves;

import org.testng.annotations.Test;
import org.testng.Assert;

@Test(groups = { "level.sanity","component.jit" })
public class UnresolvedJavaMethod extends jit.test.jitt.Test {

	static class Y {
		private boolean test = false;
		boolean state() {
			return test;
		};
		void setTrue() {
			test = true;
		}
		void setFalse() {
			test = false;
		}
	}

	static class X {
		Y y;
		public void tstFoo(int i) {

			if (i == 1) {
				if (y.state() != false)
					Assert.fail("bad state #1");
				if (i == 1) {
					y.setTrue();
					if (y.state() != true)
						Assert.fail("bad state #2");
					y.setFalse();
					if (y.state() != false)
						Assert.fail("bad state #3");
				}
			}
		}
	}

	@Test
	public void testUnresolvedJavaMethod() {
		X x = new X();
		for (int j = 0; j < sJitThreshold; j++) {
			x.tstFoo(0);
		}
		x.y = new Y();
		x.tstFoo(1);
	}
}