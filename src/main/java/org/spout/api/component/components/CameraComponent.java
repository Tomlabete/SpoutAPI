/*
 * This file is part of SpoutAPI.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
 * SpoutAPI is licensed under the SpoutDev License Version 1.
 *
 * SpoutAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * SpoutAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.api.component.components;

import org.spout.api.geo.discrete.Transform;
import org.spout.api.math.MathHelper;
import org.spout.api.math.Matrix;
import org.spout.api.render.Camera;
import org.spout.api.render.ViewFrustum;

public class CameraComponent extends EntityComponent implements Camera {
	private Matrix projection;
	private Matrix view;
	private ViewFrustum frustum = new ViewFrustum();
	private float fieldOfView = 75f;

	public CameraComponent() {

	}

	public CameraComponent(Matrix createPerspective, Matrix createLookAt) {
		projection = createPerspective;
		view = createLookAt;
	}

	@Override
	public void onAttached() {
		// TODO Get FOV
		projection = MathHelper.createPerspective(fieldOfView, 4.0f / 3.0f, .001f, 1000f);
		updateView();
	}

	@Override
	public Matrix getProjection() {
		return projection;
	}

	@Override
	public Matrix getView() {
		return view;
	}

	@Override
	public void updateView() {
		Transform transform = ((PredictableTransformComponent)getOwner().getTransform()).getRenderTransform();
		if(transform != null){
			Matrix pos = MathHelper.translate(transform.getPosition().multiply(-1));
			Matrix rot = MathHelper.rotate(transform.getRotation());
			view = pos.multiply(rot);
			frustum.update(projection, view);
		}
	}

	@Override
	public boolean canTick() {
		return false; // It's not the job of engine, if you want fluid movement, it's render job.
	}

	/*@Override
	public void onTick(float dt) {
		updateView();
	}*/

	@Override
	public ViewFrustum getFrustum() {
		return frustum;
	}
}
