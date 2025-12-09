package com.flaboy.klog.napier

import com.flaboy.klog.KLogger
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel

/**
 * Napier Antilog implementation that sends logs to KLogger.
 * KLogger handles both console output and file storage.
 * 
 * Usage:
 * ```
 * val klogger = KLogger.initialize(logPath, platformLogger)
 * val antilog = NapierKlogAntilog(klogger)
 * Napier.base(antilog)
 * ```
 */
class NapierKlogAntilog(
    private val klogger: KLogger
) : Antilog() {
    
    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?
    ) {
        val finalMessage = message ?: ""
        val finalTag = tag ?: "Napier"
        
        // Send to KLogger - it handles both console and file output
        when (priority) {
            LogLevel.VERBOSE,
            LogLevel.DEBUG,
            LogLevel.INFO -> {
                klogger.log(finalTag, finalMessage)
            }
            LogLevel.WARNING -> {
                klogger.logW(finalTag, finalMessage)
            }
            LogLevel.ERROR,
            LogLevel.ASSERT -> {
                klogger.logE(finalTag, finalMessage, throwable)
            }
        }
    }
}

