package com.github.vaerys.mixin;

import com.github.vaerys.HideFunctionOutput;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.FunctionCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(FunctionCommand.class)
public class FunctionCommandMixin {
    @Inject(method = "execute", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/command/ServerCommandSource;sendFeedback(Ljava/util/function/Supplier;Z)V",
            shift = At.Shift.BEFORE), cancellable = true)
    private static void init(ServerCommandSource source, Collection<CommandFunction> functions, CallbackInfoReturnable<Integer> cir) {
        boolean shouldHideFunctions = source.getWorld().getGameRules().getBoolean(HideFunctionOutput.HIDE_FUNCTION_OUTPUT);
        if (shouldHideFunctions) cir.setReturnValue(cir.getReturnValue());
    }
}