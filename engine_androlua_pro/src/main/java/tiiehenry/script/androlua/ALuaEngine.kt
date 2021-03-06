package tiiehenry.script.androlua

import com.luajava.LuaState
import tiiehenry.script.androlua.bridge.ALuaFuncBridge
import tiiehenry.script.androlua.bridge.ALuaVarBridge
import tiiehenry.script.androlua.eval.ALuaFileEvaluator
import tiiehenry.script.androlua.eval.ALuaReaderEvaluator
import tiiehenry.script.androlua.eval.ALuaStringEvaluator
import tiiehenry.script.androlua.internal.ALuaProvider
import tiiehenry.script.androlua.internal.ALuaPrinter
import tiiehenry.script.androlua.internal.ALuaRequirer
import tiiehenry.script.androlua.internal.ALuaRuntime
import tiiehenry.script.androlua.lang.ALuaType
import tiiehenry.script.wrapper.engine.IScriptContext
import tiiehenry.script.wrapper.engine.IScriptEngine
import tiiehenry.script.wrapper.framework.bridge.IFuncBridge
import tiiehenry.script.wrapper.framework.evaluate.IFileEvaluator
import tiiehenry.script.wrapper.framework.evaluate.IStringEvaluator
import tiiehenry.script.wrapper.framework.internal.Printable
import tiiehenry.script.wrapper.framework.internal.Requirable

class ALuaEngine(override val context: IScriptContext) : IScriptEngine<Any, ALuaType>
        , Printable, Requirable {

    override val varBridge = ALuaVarBridge(this)
    override val funcBridge: IFuncBridge<ALuaType> = ALuaFuncBridge(this)

    override val stringEvaluator = ALuaStringEvaluator(this)
    override val fileEvaluator = ALuaFileEvaluator(this)
    override val readerEvaluator = ALuaReaderEvaluator(this)

    override lateinit var printer: ALuaPrinter
    override lateinit var requirer: ALuaRequirer

    lateinit var runtime: ALuaRuntime

    lateinit var provider: ALuaProvider
    fun setDirProvider(dirProvider: ALuaProvider) {
        provider = dirProvider
    }

    override fun create() {
        runtime = ALuaRuntime(this)
        runtime.registerRuntime()


        printer = ALuaPrinter(this)
        requirer = ALuaRequirer(this)

        initVars()
    }

    private fun initVars() {
        varBridge.set("engine", runtime)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun destroy() {
        System.gc()
        runtime.L.gc(LuaState.LUA_GCCOLLECT, 1)
        runtime.L.close()
    }

}