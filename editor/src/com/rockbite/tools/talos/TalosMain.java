package com.rockbite.tools.talos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;
import com.rockbite.tools.talos.editor.NodeStage;
import com.rockbite.tools.talos.editor.UIStage;
import com.rockbite.tools.talos.editor.utils.CameraController;
import com.rockbite.tools.talos.editor.utils.DropTargetListenerAdapter;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

public class TalosMain extends ApplicationAdapter {

	private UIStage uiStage;
	private NodeStage nodeStage;
	private CameraController cameraController;

	private Skin skin;

	private DropTargetListener dropTargetListener;

	private static TalosMain instance;

	public static TalosMain Instance () {
		return instance;
	}

	public UIStage UIStage () {
		return uiStage;
	}

	public NodeStage NodeStage () {
		return nodeStage;
	}

	public DropTargetListener getDropTargetListener () {
		return dropTargetListener;
	}

	public TalosMain () {
		dropTargetListener = new DropTargetListenerAdapter() {
			@Override
			protected void doDrop (String[] finalPaths, float x, float y) {
				nodeStage.fileDrop(finalPaths, x, y);
			}
		};
	}

	@Override
	public void create () {
		TalosMain.instance = this;

		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		skin.addRegions(atlas);

		VisUI.load(skin);
		System.out.println("Created");

		uiStage = new UIStage(skin);
		nodeStage = new NodeStage(skin);

		cameraController = new CameraController((OrthographicCamera)nodeStage.getStage().getCamera());

		uiStage.init();
		nodeStage.init();

		Gdx.input.setInputProcessor(new InputMultiplexer(uiStage.getStage(), nodeStage.getStage(), cameraController));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		nodeStage.getStage().act();
		nodeStage.getStage().draw();

		uiStage.getStage().act();
		uiStage.getStage().draw();
	}

	public void resize (int width, int height) {
		nodeStage.resize(width, height);
		uiStage.resize(width, height);
	}

	@Override
	public void dispose () {
		nodeStage.getStage().dispose();
		uiStage.getStage().dispose();
	}

}
