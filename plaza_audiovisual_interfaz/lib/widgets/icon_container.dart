import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class IconContainer extends StatelessWidget {
  final double size;
  final String path;

  IconContainer({
    this.path = "http://192.168.29.141:6668/rest/files/1666536760224_avatar2.png",
    required this.size
  })
    : assert(size != null && size > 0);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: size,
      height: size,
     //
      padding: EdgeInsets.all(size * 0.15),
      child: Center(
        child: Image.network(
            path,
            width: size * 0.6,
            height: size * 0.6,
        ),
      ),
    );
  }
  
  
}