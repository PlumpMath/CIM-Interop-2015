using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    internal static class ReflectionHelper
    {
        public static T[] GetObjectsOfType<T>(object objectHierarchy)
        {
            List<T> result = new List<T>();

            Stack<object> objects = new Stack<object>();

            objects.Push(objectHierarchy);

            while (objects.Count > 0)
            {
                object nextObject = objects.Pop();

                if (nextObject == null)
                    continue;

                if (nextObject.GetType() == typeof(T) || nextObject.GetType().IsSubclassOf(typeof(T)))
                {
                    result.Add((T)nextObject);
                }
                else if (nextObject.GetType().IsArray)
                {
                    foreach (object item in (nextObject as IEnumerable))
                        objects.Push(item);
                }
                else if (nextObject.GetType().IsClass)
                {
                    foreach (FieldInfo field in nextObject.GetType().GetFields())
                    {
                        object nextNextObject = field.GetValue(nextObject);

                        if (nextNextObject != null)
                            if (field.FieldType != nextObject.GetType())
                                objects.Push(nextNextObject);
                    }
                }
            }

            return result.ToArray();
        }

        public delegate bool ReplaceObject<T>(T original, out T replacement);

        public static void ReplaceObjectsOfType<T>(object objectHierarchy, ReplaceObject<T> replaceFunction)
        {
            Stack<object> objects = new Stack<object>();

            objects.Push(objectHierarchy);

            while (objects.Count > 0)
            {
                object nextObject = objects.Pop();

                foreach (FieldInfo field in nextObject.GetType().GetFields())
                {
                    if ((field.FieldType == typeof(T)) || field.FieldType.IsSubclassOf(typeof(T)))
                    {
                        T original = (T)field.GetValue(nextObject);

                        T replacement;

                        if (replaceFunction(original, out replacement))
                            field.SetValue(nextObject, replacement);

                    }
                    else if (field.FieldType.IsArray)
                    {
                        object array = field.GetValue(nextObject);

                        if (array != null)
                        {
                            if ((field.FieldType.GetElementType() == typeof(T)) || (field.FieldType.GetElementType().IsSubclassOf(typeof(T))))
                            {
                                Array typedArray = (array as Array);

                                for (int i = 0; i < typedArray.Length; i++)
                                {
                                    T original = (T)typedArray.GetValue(i);

                                    T replacement;

                                    if (replaceFunction(original, out replacement))
                                        typedArray.SetValue(replacement, i);
                                }
                            }
                            else
                            {
                                foreach (object item in (array as IEnumerable))
                                    objects.Push(item);
                            }
                        }
                    }
                    else if (field.FieldType.IsClass)
                    {
                        object nextNextObject = field.GetValue(nextObject);

                        if (nextNextObject != null)
                            if (field.FieldType != nextObject.GetType())
                                objects.Push(nextNextObject);
                    }
                }
            }
        }

        public static void ReorderArrays(object objectHierarchy)
        {
            Random random = new Random(DateTime.Now.Millisecond);
            
            Stack<object> objects = new Stack<object>();

            objects.Push(objectHierarchy);

            while (objects.Count > 0)
            {
                object nextObject = objects.Pop();

                foreach (FieldInfo field in nextObject.GetType().GetFields())
                {
                    if (field.FieldType.IsArray)
                    {
                        IEnumerable array = field.GetValue(nextObject) as IEnumerable;

                        if (array == null)
                            continue;

                        foreach (object item in array)
                            objects.Push(item);

                        Type arrayElementType = field.FieldType.GetElementType();

                        object[] shuffledArray = array.OfType<object>().OrderBy(t => random.Next()).ToArray();

                        MethodInfo ofTypeMethod = typeof(Enumerable)
                            .GetMethod("OfType")
                            .MakeGenericMethod(new Type[] { arrayElementType });

                        var shuffledTypedEnumerable = ofTypeMethod.Invoke(null, new object[] { shuffledArray });

                        MethodInfo toArrayMethod = typeof(Enumerable)
                            .GetMethod("ToArray")
                            .MakeGenericMethod(new Type[] { arrayElementType });

                        var shuffledTypedArray = toArrayMethod.Invoke(null, new object[] { shuffledTypedEnumerable });

                        field.SetValue(nextObject, shuffledTypedArray);
                    }
                    else if (field.FieldType.IsClass)
                    {
                        object nextNextObject = field.GetValue(nextObject);

                        if (nextNextObject != null)
                            if (field.FieldType != nextObject.GetType())
                                objects.Push(nextNextObject);
                    }
                }
            }
        }
    }
}
