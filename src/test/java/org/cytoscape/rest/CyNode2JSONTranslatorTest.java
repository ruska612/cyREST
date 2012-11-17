package org.cytoscape.rest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.NetworkTestSupport;
import org.cytoscape.rest.internal.translator.CyNode2JSONTranslator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CyNode2JSONTranslatorTest {

	private Translator<String, CyNode> translator;
	private final NetworkTestSupport testSupport = new NetworkTestSupport();


	@Before
	public void setUp() throws Exception {
		translator = new CyNode2JSONTranslator();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSanity() {
		
	}
	@Test
	public void testTranslation() {
		final CyNetwork network = testSupport.getNetwork();
		final CyNode node1 = network.addNode();
		final CyNode node2 = network.addNode();
		node1.setNetworkPointer(network);
		
		//Test valid translation
		assertEquals("{\"selected\":false,\"shared name\":null,\"SUID\":"+node1.getSUID()+",\"name\":null}", translator.translate(node1));
		
		//Test updated attributes still valid translation
		network.getRow(node1).set("selected", true);
		network.getRow(node1).set("shared name", "Johnny");
		network.getRow(node1).set("name", "John Tests");
		assertEquals("{\"selected\":true,\"shared name\":\"Johnny\",\"SUID\":"+node1.getSUID()+",\"name\":\"John Tests\"}", translator.translate(node1));
		
		//Test Decimal Attribute translation
		network.getDefaultNodeTable().createColumn("DecCol", Double.class, false);
		network.getRow(node1).set("DecCol", 5.67);
		assertEquals("{\"DecCol\":5.67,\"selected\":true,\"shared name\":\"Johnny\",\"SUID\":"+node1.getSUID()+",\"name\":\"John Tests\"}", translator.translate(node1));
		
		//Test List Attribute translation
		network.getDefaultNodeTable().createListColumn("TheList", Double.class, false);
		ArrayList<Double> temp = new ArrayList<Double>();
		temp.add(5.2);
		temp.add(4.5);
		network.getRow(node1).set("TheList", temp);
		assertEquals("{\"DecCol\":5.67,\"TheList\":[5.2,4.5],\"selected\":true,\"shared name\":\"Johnny\",\"SUID\":"+node1.getSUID()+",\"name\":\"John Tests\"}", translator.translate(node1));
		
		
	}

}