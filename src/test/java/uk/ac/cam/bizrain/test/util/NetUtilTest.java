package uk.ac.cam.bizrain.test.util;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.cam.bizrain.util.NetUtil;

/**
 * Test various features of NetUtil
 * 
 * TODO: ADD MOAR TESTS
 * TODO: ADD MORE THINGS TO TEST
 * 
 * @author btfs2
 *
 */
public class NetUtilTest {

	@Test
	public void testPingUrlSimple() {
		assertTrue("Google should be up", NetUtil.pingURL("https://www.google.co.uk"));
	}

}
