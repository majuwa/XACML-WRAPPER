package de.majuwa.wrapper;

import com.att.research.xacml.api.pdp.PDPEngineFactory;
import com.att.research.xacml.api.pdp.PDPException;
import com.att.research.xacml.std.dom.DOMRequest;
import com.att.research.xacml.std.dom.DOMStructureException;
import com.att.research.xacml.util.FactoryException;

import java.io.File;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        final var properties = new Properties();
        properties.put("xacml.dataTypeFactory", "com.att.research.xacml.std.StdDataTypeFactory");
        properties.put("xacml.pdpEngineFactory", "com.att.research.xacmlatt.pdp.ATTPDPEngineFactory");
        properties.put("xacml.pepEngineFactory", "com.att.research.xacml.std.pep.StdEngineFactory");
        properties.put("xacml.pipFinderFactory", "com.att.research.xacml.std.pip.StdPIPFinderFactory");
        properties.put("xacml.att.evaluationContextFactory",
                "com.att.research.xacmlatt.pdp.std.StdEvaluationContextFactory");
        properties.put("xacml.att.combiningAlgorithmFactory",
                "com.att.research.xacmlatt.pdp.std.StdCombiningAlgorithmFactory");
        properties.put("xacml.att.functionDefinitionFactory",
                "com.att.research.xacmlatt.pdp.std.StdFunctionDefinitionFactory");
        properties.put("xacml.att.policyFinderFactory", "com.att.research.xacmlatt.pdp.std.StdPolicyFinderFactory");
        properties.put("xacml.att.stdPolicyFinderFactory.rootPolicyFile", "properties");
        properties.put("xacml.rootPolicies", "properties");
        properties.put("xacml.referencedPolicies", "properties");
        properties.put("properties.file", args[0]);


        try {
            var engine = PDPEngineFactory.newInstance().newEngine(properties);
            final var actualRequest = DOMRequest.load(new File(args[1]));
            final var response = engine.decide(actualRequest);
            System.out.println(response.getResults().iterator().next().getDecision());
        } catch (FactoryException | DOMStructureException | PDPException e) {
            e.printStackTrace();
        }
    }
}