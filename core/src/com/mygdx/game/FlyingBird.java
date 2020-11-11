package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.awt.GraphicsDevice;
import java.util.Random;

public class FlyingBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img,bird, poo1, poo2, poo3;
	float bw, bh, sh, sw, bx, by;
	float velocity = 0.0f;
	float gravity = 0.5f;
	int npoox = 3;
	float poosx[] = new float[npoox];
	float poosy[][] = new float[3][npoox];
	ShapeRenderer sr;
	Circle c_bird, c_poo1[], c_poo2[], c_poo3[];
	int state = 0;
	int score = 0;
	Boolean flag = true;
	BitmapFont font;
	BitmapFont font1;
	Sound sound;
	Boolean flag1 = true;



	@Override
	public void create () {

		batch = new SpriteBatch();
		img = new Texture("lvl1.png");
		bird = new Texture("bird1.png");
		poo1 = new Texture("layer1.png");
		poo2 = new Texture("layer2.png");
		poo3 = new Texture("layer3.png");

		bw = Gdx.graphics.getWidth()/13;
		bh = Gdx.graphics.getHeight()/8;
		sh = Gdx.graphics.getHeight();
		sw = Gdx.graphics.getWidth();
		bx = Gdx.graphics.getWidth()/5;
		by = Gdx.graphics.getHeight();

		font =  new BitmapFont();
		font.setColor(Color.RED);
		font.getData().setScale(8);

		font1 = new BitmapFont();
		font1.setColor(Color.GREEN);
		font1.getData().setScale(10);

		sound = Gdx.audio.newSound(Gdx.files.internal("original.ogg"));

		c_bird = new Circle();
		c_poo1 = new Circle[npoox];
		c_poo2 = new Circle[npoox];
		c_poo3 = new Circle[npoox];

		sr = new ShapeRenderer();

		for(int i = 0; i < npoox; i++) {
			poosx[i] = sw + i *sw/2;
			Random r1 = new Random();
			Random r2 = new Random();
			Random r3 = new Random();
			poosy[0][i]  = r1.nextFloat() * sh;
			poosy[1][i]  = r2.nextFloat() * sh;
			poosy[2][i]  = r3.nextFloat() * sh;
			c_poo1[i] = new Circle();
			c_poo2[i] = new Circle();
			c_poo3[i] = new Circle();

		}

	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(bird, bx, by, bw, bh);

		if (state == 1) {
			if (Gdx.input.justTouched()) {
				velocity = -18;
			}
			flag1 = true;
			for (int i = 0; i < npoox; i++) {

				if (poosx[i] < 0) {
					flag = true;
					poosx[i] = npoox * sw / 2;
					Random r1 = new Random();
					Random r2 = new Random();
					Random r3 = new Random();
					poosy[0][i] = r1.nextFloat() * sh;
					poosy[1][i] = r2.nextFloat() * sh;
					poosy[2][i] = r3.nextFloat() * sh;
				}
				font.draw(batch,String.valueOf(score),sw-bw, bh);
				if (bx > poosx[i] && flag){
					score++;
					System.out.println(score);
					flag = false;
				}


				poosx[i] = poosx[i] - 12;
				batch.draw(poo1, poosx[i], poosy[0][i], bw, bh);
				batch.draw(poo2, poosx[i], poosy[1][i], bw, bh);
				batch.draw(poo3, poosx[i], poosy[2][i], bw, bh);
			}

			if (by < bh) {
				by = sw / 2;
				velocity = 0;
				state = 2;
			} else {
				velocity = velocity + gravity;
				by = by - velocity;
			}
		} else if (state == 2){
			font1.draw(batch, "You lost",0, sh/2);
			if(flag1) {
				sound.play();
				flag1 = false;
			}
			if (Gdx.input.justTouched()) {
				bx = Gdx.graphics.getWidth()/5;
				by = Gdx.graphics.getHeight();
				score = 0;
				for(int i = 0; i < npoox; i++) {
					poosx[i] = sw + i *sw/2;
					Random r1 = new Random();
					Random r2 = new Random();
					Random r3 = new Random();
					poosy[0][i]  = r1.nextFloat() * sh;
					poosy[1][i]  = r2.nextFloat() * sh;
					poosy[2][i]  = r3.nextFloat() * sh;
					c_poo1[i] = new Circle();
					c_poo2[i] = new Circle();
					c_poo3[i] = new Circle();
				}
				state = 1;
			}
		}
		else if (state == 0) {
			font1.draw(batch, "Tap to play",0, sh/2);
			if (Gdx.input.justTouched()) {
				state = 1;
			}
		}
		c_bird.set(bx + bw/2, by + bh/2, bw / 2);
		//sr.begin(ShapeRenderer.ShapeType.Filled);
		for (int i = 0; i < npoox; i++) {
		/*	sr.setColor(Color.BLUE);
			sr.circle(poosx[i] + bw/2, poosy[0][i] + bh/2, bw / 2);
			sr.circle(poosx[i] + bw/2, poosy[1][i] + bh/2, bw / 2);
			sr.circle(poosx[i] + bw/2, poosy[2][i] + bh/2, bw / 2);
			sr.circle(bx + bw/2, by + bh/2, bw / 2);
			*/
			c_poo1[i].set(poosx[i] + bw/2, poosy[0][i] + bh/2, bw / 2);
			c_poo2[i].set(poosx[i] + bw/2, poosy[1][i] + bh/2, bw / 2);
			c_poo3[i].set(poosx[i] + bw/2, poosy[2][i] + bh/2, bw / 2);
			if(Intersector.overlaps(c_bird, c_poo1[i]) || Intersector.overlaps(c_bird, c_poo2[i]) || Intersector.overlaps(c_bird, c_poo3[i])) {
				state = 2;
			}
		}
	//	sr.end();
	batch.end();
	}

	@Override
	public void dispose () {

	}
}
