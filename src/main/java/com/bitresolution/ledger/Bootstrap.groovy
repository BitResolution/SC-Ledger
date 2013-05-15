package com.bitresolution.ledger

import org.codehaus.groovy.control.CompilerConfiguration;

class Bootstrap {

    public static void main(String... args) {
        def compiler = new CompilerConfiguration()
        compiler.setScriptBaseClass(LedgerScript.class.name)

        GroovyShell shell = new GroovyShell(this.class.classLoader, new Binding(), compiler)
//        shell.evaluate(args[0])
        File script = new File("scripts/test-questions.groovy")
        shell.evaluate(script)
    }
}
