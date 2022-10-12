import 'package:flutter/material.dart';

class Rectangle extends StatelessWidget {
  final double size;
  final List<Color> colors;


  Rectangle({required this.size, required this.colors})
      : assert(size >0.00),
        assert(colors.length >= 2);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: size,
      height: size,
      decoration: BoxDecoration(
        shape: BoxShape.rectangle,
        gradient: LinearGradient(
            colors: colors,
            begin: Alignment.topCenter,
            end: Alignment.bottomCenter
        )
      ),
    );
  }

}