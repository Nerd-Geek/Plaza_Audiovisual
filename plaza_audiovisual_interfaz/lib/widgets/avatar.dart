import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class Avatar extends StatelessWidget {
  final double? imageSize;
  final String? path;

  const Avatar({
    this.imageSize = 100,
    required this.path,
  });

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Container(
          margin: const EdgeInsets.all(10),
          decoration: const BoxDecoration(
            color: Colors.white,
            shape: BoxShape.circle,
            boxShadow: [
              BoxShadow(
                  offset: Offset(0,20),
                  blurRadius: 20,
                  color: Colors.black26),
            ],
          ),
          child: ClipOval(
            child: Image.network(
              path??"http://192.168.29.141:6668/rest/files/1667208375733_loading_gif.gif",
              width: imageSize,
              height: imageSize,
              fit: BoxFit.cover,
            ),
          ),
        ),
      ],
    );
  }

}