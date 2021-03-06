package tiiehenry.script.androlua

import tiiehenry.script.androlua.lang.ALuaType
import tiiehenry.script.wrapper.engine.IScriptContext
import tiiehenry.script.wrapper.engine.IScriptEngineFactory
import tiiehenry.script.wrapper.framework.internal.GlobalScriptContext

class ALuaEngineFactory: IScriptEngineFactory<ALuaEngine, Any, ALuaType> {
    override val engineName: String="Rhino"
    override val engineVersion: String="1.7.22"
    override val scriptNames: List<String> = listOf("Lua")
    override val mimeTypes: List<String> = listOf()
    override val globalScriptContext: IScriptContext = GlobalScriptContext(System.`in`, System.out, System.err)

    override fun newScriptEngine(scriptContext: IScriptContext): ALuaEngine {
        return ALuaEngine(scriptContext)
    }


}