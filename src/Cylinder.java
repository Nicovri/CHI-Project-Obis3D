	public Cylinder createCylinder(Segment segment) {
        // x axis vector is <1,0,0>
        // y axis vector is <0,1,0>
        // z axis vector is <0,0,1>
        // angle = arccos((P*Q)/(|P|*|Q|))
        // define a point representing the Y axis
        Point3D yAxis = new Point3D(0,1,0);
        // define a point based on the difference of our end point from the start point of our segment
        Point3D seg = segment.getEnd().subtract(segment.getStart());
        // determine the length of our line or the height of our cylinder object
        double height = seg.magnitude();
        // get the midpoint of our line segment
        Point3D midpoint = segment.getEnd().midpoint(segment.getStart());
        // set up a translate transform to move to our cylinder to the midpoint
        Translate moveToMidpoint = new Translate(midpoint.getX(), midpoint.getY(), midpoint.getZ());
        // get the axis about which we want to rotate our object
        Point3D axisOfRotation = seg.crossProduct(yAxis);
        // get the angle we want to rotate our cylinder
        double angle = Math.acos(seg.normalize().dotProduct(yAxis));
        // create our rotating transform for our cylinder object
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);
        // create our cylinder object representing our line
        Cylinder line = new Cylinder(0.01, height);
        // add our two transfroms to our cylinder object
        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    } // end of the createCylinder method
