package mDimension.core;


import arc.*;
import arc.assets.loaders.TextureLoader.*;
import arc.files.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.Renderer;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.maps.*;
import mindustry.type.*;
import mindustry.world.blocks.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class ExtendedRenderer extends Renderer {
    private @Nullable LaunchAnimator launchAnimator;
    private final Color clearColor = new Color(0f, 0f, 0f, 1f);

    public ExtendedRenderer(){
        super();
    }

    @Override
    public void init() {
        super.init();
    }
    @Override
    public void draw(){
        Events.fire(Trigger.preDraw);
        MapPreviewLoader.checkPreviews();

        camera.update();

        if(Float.isNaN(camera.position.x) || Float.isNaN(camera.position.y)){
            camera.position.set(player);
        }

        graphics.clear(clearColor);
        Draw.reset();

        if(animateWater || animateShields){
            effectBuffer.resize(graphics.getWidth(), graphics.getHeight());
        }

        Draw.proj(camera);

        blocks.checkChanges();
        blocks.floor.checkChanges();
        blocks.processBlocks();

        Draw.sort(true);

        Events.fire(Trigger.draw);
        MapPreviewLoader.checkPreviews();

        if(renderer.pixelate){
            pixelator.register();
        }

        Draw.draw(Layer.background, this::drawBackground);
        Draw.draw(Layer.floor, blocks.floor::drawFloor);
        Draw.draw(Layer.block - 1, blocks::drawShadows);
        Draw.draw(Layer.block - 0.09f, () -> {
            blocks.floor.beginDraw();
            blocks.floor.drawLayer(CacheLayer.walls);
        });

        Draw.drawRange(Layer.blockBuilding, () -> Draw.shader(Shaders.blockbuild, true), Draw::shader);

        //render all matching environments
        for(var renderer : envRenderers){
            if((renderer.env & state.rules.env) == renderer.env){
                renderer.renderer.run();
            }
        }

        if(state.rules.lighting && drawLight){
            Draw.draw(Layer.light, lights::draw);
        }

        if(enableDarkness){
            Draw.draw(Layer.darkness, blocks::drawDarkness);
        }

        if(bloom != null){
            bloom.resize(graphics.getWidth(), graphics.getHeight());
            bloom.setBloomIntensity(settings.getInt("bloomintensity", 6) / 4f + 1f);
            bloom.blurPasses = settings.getInt("bloomblur", 1);
            Draw.draw(Layer.bullet - 0.02f, bloom::capture);
            Draw.draw(Layer.effect + 0.02f, bloom::render);
            bloom.resize(graphics.getWidth(), graphics.getHeight());
            bloom.setBloomIntensity(settings.getInt("bloomintensity", 6) / 1f + 1f);
            bloom.blurPasses = settings.getInt("bloomblur", 1);
            Draw.draw(160 - 0.02f, bloom::capture);
            Draw.draw(170+ 0.02f, bloom::render);
        }

        control.input.drawCommanded();

        Draw.draw(Layer.plans, overlays::drawBottom);

        if(animateShields && Shaders.shield != null){
            //TODO would be nice if there were a way to detect if any shields or build beams actually *exist* before beginning/ending buffers, otherwise you're just blitting and swapping shaders for nothing
            Draw.drawRange(Layer.shields, 1f, () -> effectBuffer.begin(Color.clear), () -> {
                effectBuffer.end();
                effectBuffer.blit(Shaders.shield);
            });

            Draw.drawRange(Layer.buildBeam, 1f, () -> effectBuffer.begin(Color.clear), () -> {
                effectBuffer.end();
                effectBuffer.blit(Shaders.buildBeam);
            });
        }

        float scaleFactor = 4f / renderer.getDisplayScale();

        //draw objective markers
        state.rules.objectives.eachRunning(obj -> {
            for(var marker : obj.markers){
                if(marker.world){
                    marker.draw(marker.autoscale ? scaleFactor : 1);
                }
            }
        });

        for(var marker : state.markers){
            if(marker.world){
                marker.draw(marker.autoscale ? scaleFactor : 1);
            }
        }

        Draw.reset();

        Draw.draw(Layer.overlayUI, overlays::drawTop);
        if(state.rules.fog) Draw.draw(Layer.fogOfWar, fog::drawFog);
        Draw.draw(Layer.space, () -> {
            if(launchAnimator == null || landTime <= 0f) return;
            launchAnimator.drawLaunch();
        });
        if(launchAnimator != null){
            Draw.z(Layer.space);
            launchAnimator.drawLaunchGlobalZ();
            Draw.reset();
        }

        Events.fire(Trigger.drawOver);
        blocks.drawBlocks();

        Groups.draw.draw(Drawc::draw);

        if(drawDebugHitboxes){
            DebugCollisionRenderer.draw();
        }

        Draw.reset();
        Draw.flush();
        Draw.sort(false);

        Events.fire(Trigger.postDraw);
    }

}
